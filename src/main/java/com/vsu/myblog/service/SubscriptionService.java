package com.vsu.myblog.service;

import com.vsu.myblog.dto.subscription.SubscriptionDto;
import com.vsu.myblog.exception.BadRequestException;
import com.vsu.myblog.exception.NotFoundException;
import com.vsu.myblog.mapper.SubscriptionMapper;
import com.vsu.myblog.model.entity.SubscriptionEntity;
import com.vsu.myblog.model.entity.UserEntity;
import com.vsu.myblog.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    private final SubscriptionMapper subscriptionMapper;

    public List<SubscriptionDto> getCurrentSubscriptions() {
        UserEntity user = userService.getCurrentUserEntity();
        return user.getSubscriptions().stream().map(subscriptionMapper::toDto).collect(Collectors.toList());
    }

    public List<SubscriptionDto> getCurrentSubscribers() {
        UserEntity user = userService.getCurrentUserEntity();
        return user.getSubscribers().stream().map(subscriptionMapper::toDto).collect(Collectors.toList());
    }

    public List<SubscriptionDto> getSubscriptionsByUserId(Long id) {
        UserEntity user = userService.getUserEntityById(id);
        return user.getSubscriptions().stream().map(subscriptionMapper::toDto).collect(Collectors.toList());
    }

    public List<SubscriptionDto> getSubscribersByUserId(Long id) {
        UserEntity user = userService.getUserEntityById(id);
        return user.getSubscribers().stream().map(subscriptionMapper::toDto).collect(Collectors.toList());
    }

    public SubscriptionDto subscribe(Long id){
        UserEntity user = userService.getCurrentUserEntity();
        UserEntity userSubscription = userService.getUserEntityById(id);
        SubscriptionEntity subscription = new SubscriptionEntity();
        SubscriptionEntity result;
        if (Objects.equals(user.getId(), id)) {
            log.info("Can't subscribe to yourself");
            throw new BadRequestException("Can't subscribe to yourself");
        }

        if (!subscriptionRepository.existsByUserAndSubscription(user, userSubscription)) {
            subscription.setUser(user);
            subscription.setSubscription(userSubscription);
            result = subscriptionRepository.save(subscription);
            log.info("Subscription saved");
        } else {
            log.info("Subscription already defined");
            throw new BadRequestException("Subscription already defined");
        }
        return subscriptionMapper.toDto(result);
    }

    public void unsubscribe(Long id) {
        UserEntity user = userService.getCurrentUserEntity();
        UserEntity userSubscription = userService.getUserEntityById(id);
        if (Objects.equals(user.getId(), id)) {
            log.info("Can't unsubscribe to yourself");
            throw new BadRequestException("Can't unsubscribe to yourself");
        }
        SubscriptionEntity subscription = subscriptionRepository.findByUserAndSubscription(user, userSubscription)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (subscriptionRepository.existsByUserAndSubscription(user, userSubscription)) {
            subscription.setUser(user);
            subscription.setSubscription(userSubscription);
            subscriptionRepository.delete(subscription);
            user.getSubscriptions().remove(subscription);
            userSubscription.getSubscribers().remove(subscription);
            log.info("Subscription deleted");
        } else {
            log.info("Subscription not exists");
            throw new NotFoundException("Subscription not exists");
        }
    }

    public boolean isSubscribedToUser(Long id) {
        UserEntity user = userService.getCurrentUserEntity();
        UserEntity userSubscription = userService.getUserEntityById(id);
        if (Objects.equals(user.getId(), id)) {
            log.info("Can't check subscription to yourself");
            throw new BadRequestException("Can't check subscription to yourself");
        }
        return subscriptionRepository.existsByUserAndSubscription(user, userSubscription);
    }

    public boolean isSubscribedToUserOrIsItCurrentUser(Long id) {
        UserEntity user = userService.getCurrentUserEntity();
        UserEntity userSubscription = userService.getUserEntityById(id);
        if (Objects.equals(user.getId(), id)) {
            return true;
        }
        return subscriptionRepository.existsByUserAndSubscription(user, userSubscription);
    }
}
