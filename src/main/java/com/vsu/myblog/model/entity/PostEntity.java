package com.vsu.myblog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "privacy")
    @NotNull
    private String privacy;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<LikeEntity> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
