package com.vsu.myblog.controller;

import com.vsu.myblog.dto.subscription.SubscriptionDto;
import com.vsu.myblog.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
@Tag(name="Подписки", description = "API для работы с подписками и подписчиками")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @Secured("ROLE_USER")
    @Operation(summary = "Получить свои подписки", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<SubscriptionDto>> getCurrentSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getCurrentSubscriptions());
    }

    @GetMapping("/subscribers")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить своих подписчиков", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<SubscriptionDto>> getCurrentSubscribers() {
        return ResponseEntity.ok(subscriptionService.getCurrentSubscribers());
    }

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить подписки пользователя по id пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByUserId(id));
    }

    @GetMapping("/subscribers/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Получить подписчиков пользователя по id пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<List<SubscriptionDto>> getSubscribersByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getSubscribersByUserId(id));
    }

    @PostMapping("/subscribe/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Подписаться на пользователя по id пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<SubscriptionDto> subscribeByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(subscriptionService.subscribe(id), HttpStatus.CREATED);
    }

    @DeleteMapping("/unsubscribe/{id}")
    @Secured("ROLE_USER")
    @Operation(summary = "Отписаться от пользователя по id пользователя", description = "Доступ: ROLE_USER")
    public ResponseEntity<Void> unsubscribeByUserId(@PathVariable Long id) {
        subscriptionService.unsubscribe(id);
        return ResponseEntity.ok().build();
    }
}
