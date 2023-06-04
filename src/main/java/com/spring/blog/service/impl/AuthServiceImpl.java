package com.spring.blog.service.impl;

import com.spring.blog.exception.BlogAPIException;
import com.spring.blog.model.Role;
import com.spring.blog.model.User;
import com.spring.blog.payload.LoginDto;
import com.spring.blog.payload.RegisterDto;
import com.spring.blog.repository.RoleRepository;
import com.spring.blog.repository.UserRepository;
import com.spring.blog.security.JwtTokenProvider;
import com.spring.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    // get access to password encoder to user when enrolling new user to "db.users"
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    // get access to the auth Manager to verify if a user exist/not
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository repository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        // authenticate with user provided credentials
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
               loginDto.getUsernameOrEmail(), loginDto.getPassword()));

      // store authentication returned object in the spring security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // return token after login
        String token = jwtTokenProvider.generateToken(authentication);

      return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
      // check if user already exists
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        // check if email exist in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        // encode password before save in db
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // add role to user
        Set<Role> roles = new HashSet<>();
        // by default a newly registered user to our blog app is of "USER" type
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        // add role to set
        roles.add(userRole);
        // set role to user object being registered
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }
}
