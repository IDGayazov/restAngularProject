package com.example.Instazoo.service;

import com.example.Instazoo.entity.User;
import com.example.Instazoo.entity.enums.ERole;
import com.example.Instazoo.exceptions.UserExistException;
import com.example.Instazoo.payload.request.SignupRequest;
import com.example.Instazoo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(OncePerRequestFilter.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setName(signupRequest.getFirstname());
        user.setLastname(signupRequest.getLastname());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try{
            LOG.info("Saving User {}", signupRequest.getEmail());
            return userRepository.save(user);
        }catch(Exception e){
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + "is already exist");
        }
    }
}
