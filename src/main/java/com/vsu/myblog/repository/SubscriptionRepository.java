package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.SubscriptionEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    List<SubscriptionEntity> findAllByUser(UserEntity user);

    List<SubscriptionEntity> findAllBySubscription(UserEntity subscription);

    List<SubscriptionEntity> findAllByUserId(Long userId);

    List<SubscriptionEntity> findAllBySubscriptionId(Long subscriptionId);
}
