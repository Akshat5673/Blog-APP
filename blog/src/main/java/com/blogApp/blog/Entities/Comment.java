package com.blogApp.blog.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String commentContent;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
