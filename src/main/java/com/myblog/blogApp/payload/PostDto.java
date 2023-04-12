package com.myblog.blogApp.payload;
import com.myblog.blogApp.entities.AbstractClass;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto extends AbstractClass {

    @NotNull
    private int likes;

    @NotNull
    @Size(min=2,message = "Post TITLE should have atleast 2 characters")
    private String title;

    @NotNull
    @Size(min=5,message = "Post CONTENTS should have atleast 5 characters")
    private String contents;

    @NotNull
    @Size(min=10,message = "Post DESCRIPTION should have atleast 10 characters")
    private String description;
}
