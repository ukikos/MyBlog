package com.vsu.myblog.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SubscriptionEntity> subsriptions;

    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    private List<SubscriptionEntity> subsribers;

}
