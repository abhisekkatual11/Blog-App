package com.myblog.blogApp.security;

import com.myblog.blogApp.entities.Role;
import com.myblog.blogApp.entities.User;
import com.myblog.blogApp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User users = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username or email")
                );
        return new org.springframework.security.core.userdetails
                .User(users.getUsername(),users.getPassword(),
                mapToRolesAuthorities(users.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapToRolesAuthorities(Set<Role> roles) {
        List<SimpleGrantedAuthority> list = roles.stream().map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());
        return list;
    }
}
