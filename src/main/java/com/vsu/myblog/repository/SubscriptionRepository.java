package com.vsu.myblog.repository;

import com.vsu.myblog.model.entity.SubscriptionEntity;
import com.vsu.myblog.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    List<SubscriptionEntity> findAllByUser(UserEntity user);

    List<SubscriptionEntity> findAllBySubscription(UserEntity subscription);

    List<SubscriptionEntity> findAllByUserId(Long userId);

    List<SubscriptionEntity> findAllBySubscriptionId(Long subscriptionId);

    Optional<SubscriptionEntity> findByUserAndSubscription(UserEntity user, UserEntity subscription);

    Optional<SubscriptionEntity> findByUserIdAndSubscriptionId(Long userId, Long subscriptionId);

    boolean existsByUserAndSubscription(UserEntity user, UserEntity subscription);

    boolean existsByUserIdAndSubscriptionId(Long userId, Long subscriptionId);
}
