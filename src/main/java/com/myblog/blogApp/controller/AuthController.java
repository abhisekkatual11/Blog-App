package com.myblog.blogApp.controller;

import com.myblog.blogApp.entities.Role;
import com.myblog.blogApp.entities.User;
import com.myblog.blogApp.payload.LoginDto;
import com.myblog.blogApp.payload.SignUpDto;
import com.myblog.blogApp.repository.RoleRepository;
import com.myblog.blogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    //Field authenticationManager in com.myblog.blogApp.controller.
    // AuthController required a bean of type 'org.springframework.security.authentication.AuthenticationManager' that could not be found.
    @Autowired//===============THIS WILL VERIFY MY USERNAME AND PASSWORD===================================================================
    private AuthenticationManager authenticationManager;//--------------------------->Not automatically created

    @Autowired
    private UserRepository userRepository;
    //to create the bean of AuthenticationManager we go to the SECURITYCONFIGFILE and apply (
//  11*                         public AuthenticationManager authenticationManagerBean() throws Exception {
//                                     return super.authenticationManagerBean();
//                              }
//                           )

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        //postman call the AUTH CONTROLLER
        //Auth CON has signin method
        //it takes the JSON and put that into LOGINDTO
        //to verify the username and Password we required AUTHENTICATION MANAGER class
        //              to do this go to SECURITYCONFIGFILE and override method and apply @BEAN ANNOTATION
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );//->if the details are "correct/OR VALID" THEN it move further and "CREATE THE TOKEN"
        //                              otherwise STOP HERE. -> and gives BAD CREDENTIAL in Postman

//        System.out.println(authenticate);

        //->->->->->->->->->if it is true then <- <- <- <- <- <- <- <- <- <- <- <-
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return new ResponseEntity<>("User sign In Successfully", HttpStatus.OK);
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto singUpDto){
        if(userRepository.existsByUsername(singUpDto.getUsername())){
            return  new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(singUpDto.getEmail())){
            return  new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        User user=new User();
        user.setName(singUpDto.getName());
        user.setUsername(singUpDto.getUsername());
        user.setEmail(singUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(singUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully",HttpStatus.OK);
    }
}
