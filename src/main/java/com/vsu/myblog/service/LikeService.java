package com.vsu.myblog.service;

import com.vsu.myblog.dto.post.LikeDto;
import com.vsu.myblog.exception.BadRequestException;
import com.vsu.myblog.exception.NotFoundException;
import com.vsu.myblog.mapper.LikeMapper;
import com.vsu.myblog.model.entity.LikeEntity;
import com.vsu.myblog.model.entity.PostEntity;
import com.vsu.myblog.model.entity.UserEntity;
import com.vsu.myblog.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserService userService;
    private final PostService postService;
    private final SubscriptionService subscriptionService;
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    public List<LikeDto> getAllCurrentLikes() {
        return userService.getCurrentUserEntity().getLikes().stream().map(likeMapper::toDto)
                .collect(Collectors.toList());
    }

    public LikeDto likePostByPostId(Long postId) {
        PostEntity post = postService.getPostEntityByPostId(postId);
        UserEntity user = userService.getCurrentUserEntity();
        if (!likeRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            LikeEntity like = new LikeEntity();
            like.setUser(user);
            like.setPost(post);
            return likeMapper.toDto(likeRepository.save(like));
        } else {
            log.info("Like on post with id: {} already exists", postId);
            throw new BadRequestException("Like on post with id: "+postId+" already exists");
        }
    }

    public void deleteLikeByPostId(Long postId) {
        PostEntity post = postService.getPostEntityByPostId(postId);
        UserEntity user = userService.getCurrentUserEntity();
        if (likeRepository.existsByUserIdAndPostId(user.getId(), post.getId())) {
            LikeEntity like = likeRepository.findByUserIdAndPostId(user.getId(), postId)
                    .orElseThrow(() -> {
                        log.info("Like on post with id: {} not found", postId);
                        throw new NotFoundException("Like on post with id: "+postId+" not found");
                    });
            likeRepository.deleteById(like.getId());
        } else {
            log.info("Like on post with id: {} not found", postId);
            throw new NotFoundException("Like on post with id: "+postId+" not found");
        }
    }
}
