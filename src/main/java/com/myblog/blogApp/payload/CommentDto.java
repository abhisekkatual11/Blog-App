package com.myblog.blogApp.payload;
import com.myblog.blogApp.entities.AbstractClass;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto extends AbstractClass {

    private String name;

    private String email;

    private String body;
}
