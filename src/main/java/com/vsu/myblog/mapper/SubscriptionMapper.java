package com.vsu.myblog.mapper;

import com.vsu.myblog.dto.subscription.SubscriptionCreateDto;
import com.vsu.myblog.dto.subscription.SubscriptionDto;
import com.vsu.myblog.model.entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubscriptionMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "subscriptionId", target = "subscription.id")
    SubscriptionEntity toEntity(SubscriptionDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "subscription.id", target = "subscriptionId")
    SubscriptionDto toDto(SubscriptionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "subscriptionId", target = "subscription.id")
    SubscriptionEntity toEntity(SubscriptionCreateDto dto);
}
