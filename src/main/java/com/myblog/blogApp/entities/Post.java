package com.myblog.blogApp.entities;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
//import java.util.HashSet;
//import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="posts")
public class Post extends AbstractClass {

    @Column(nullable = false)
    private int likes;

    @Column(nullable = false,unique = true)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy ="post",cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Comment> comments=new HashSet<>();
}
