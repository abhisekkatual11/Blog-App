package com.myblog.blogApp.payload;

import com.myblog.blogApp.entities.AbstractClass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpDto extends AbstractClass {
    private String name;
    private String email;
    private String username;
    private String password;
}
