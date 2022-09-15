package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.SubscriptionEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    List<SubscriptionEntity> findAllByUser(UserEntity user);

    List<SubscriptionEntity> findAllBySubscription(UserEntity subscription);

    List<SubscriptionEntity> findAllByUserId(Long userId);

    List<SubscriptionEntity> findAllBySubscriptionId(Long subscriptionId);
}
