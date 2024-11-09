package com.blogApp.blog.Entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id;

     @Column(name = "post_title", nullable = false, length = 100,unique = true)
     private String postTitle;

     @Column(name = "post_content", nullable = false, length = 1000)
     private String postContent;

     @Column(name = "image_name")
     private String imageName;

     @Column(name = "post_date")
     private Date postDate;

     @ManyToOne
     @JoinColumn(name = "category_id")
     private Category category;

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;

     @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<Comment> comments = new ArrayList<>();

}
