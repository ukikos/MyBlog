package com.vsu.myblog.service;

import com.vsu.myblog.dto.post.PostCreateOrUpdateDto;
import com.vsu.myblog.dto.post.PostDto;
import com.vsu.myblog.exception.BadRequestException;
import com.vsu.myblog.exception.NotFoundException;
import com.vsu.myblog.mapper.PostMapper;
import com.vsu.myblog.model.entity.PostEntity;
import com.vsu.myblog.model.entity.UserEntity;
import com.vsu.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final PostMapper postMapper;

    public List<PostDto> getCurrentPosts() {
        UserEntity user = userService.getCurrentUserEntity();
        return user.getPosts().stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    public List<PostDto> getPostsByUserId(Long id) {
        if (subscriptionService.isSubscribedToUserOrIsItCurrentUser(id) || Objects.equals(userService.getCurrentId(), id)) {
            return postRepository.findAllByUserId(id).stream().map(postMapper::toDto).collect(Collectors.toList());
        } else {
            return postRepository.findAllNonPrivateByUserId(id).stream().map(postMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    public PostDto getPostByPostId(Long id) {
        if (subscriptionService.isSubscribedToUserOrIsItCurrentUser(id) || Objects.equals(userService.getCurrentId(), id)) {
            return postMapper.toDto(postRepository.findById(id).orElseThrow(() -> {
                log.info("Post with id: {} not exists", id);
                throw new NotFoundException("Post with id: "+id+" not exists");
            }));
        } else {
            return postMapper.toDto(postRepository.findNonPrivateById(id).orElseThrow(() -> {
                throw new NotFoundException("Non-private post with id: "+id+" not exists");
            }));
        }
    }

    public PostDto createPost(PostCreateOrUpdateDto requestDto) {
        if (requestDto.getContent().isBlank()) {
            throw new BadRequestException("Post content is empty");
        }
        if (!Objects.equals(requestDto.getPrivacy(), "None") && !Objects.equals(requestDto.getPrivacy(), "Private")) {
            throw new BadRequestException("Post privacy is not correct");
        }
        PostEntity newPost = postMapper.toEntity(requestDto);
        newPost.setUser(userService.getCurrentUserEntity());
        PostEntity result = postRepository.save(newPost);
        return postMapper.toDto(result);
    }

    public PostDto editPostById(Long id, PostCreateOrUpdateDto updateDto) {
        if (updateDto.getContent().isBlank()) {
            throw new BadRequestException("Post content is empty");
        }
        if (!Objects.equals(updateDto.getPrivacy(), "None") && !Objects.equals(updateDto.getPrivacy(), "Private")) {
            throw new BadRequestException("Post privacy is not correct");
        }
        if (postRepository.existsByIdAndUserId(id, userService.getCurrentId())) {
            PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> {
                log.info("Post with id: {} not found", id);
                throw new NotFoundException("Post with id: "+id+" not found");
            });
            PostDto postDto = postMapper.toDto(postEntity);
            PostDto updatedPostDto = postMapper.updateDto(postDto, updateDto);
            postRepository.save(postMapper.toEntity(updatedPostDto));
            return updatedPostDto;
        } else {
            log.info("Post with id: {} not found or not your", id);
            throw new NotFoundException("Post with id: "+id+" not found or not your");
        }
    }

    public void deletePostById(Long id) {
        if (postRepository.existsByIdAndUserId(id, userService.getCurrentId())) {
            postRepository.deleteById(id);
        } else if (postRepository.existsById(id) && !postRepository.existsByIdAndUserId(id, userService.getCurrentId())) {
            log.info("Post with id: {} not your", id);
            throw new NotFoundException("Post with id: "+id+" not your");
        } else {
            log.info("Post with id: {} not found", id);
            throw new BadRequestException("Post with id: "+id+" not found");
        }
    }

}
