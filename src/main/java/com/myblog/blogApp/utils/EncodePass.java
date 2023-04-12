package com.myblog.blogApp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePass {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("admin"));
        //$2a$10$5awaepAq8mCLnUd0eDEx1OHyGxyWZ2lMlWD5u4.s1mrCH1vwHWXoy--------->("Abhisek565@")
        //$2a$10$PBfOMPG4PRG7D0JHYLIdfO2qVL87dIe0Bh6IW0/Xj0O0B1xICMAVu--------->("Admin@")
        //$2a$10$SyteAE4Sb4ldsbZdbsZcUunMqpG1ZY9Hl1zS2mJB7xPQKWqkGcEQm
        //
    }
}
