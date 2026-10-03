package com.sparktech.finalproject.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;



    public User registerUser(User user){



        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );



        if(user.getRole()==null || user.getRole().isEmpty()){

            user.setRole("CUSTOMER");

        }



        return userRepository.save(user);

    }





    public boolean emailExists(String email){


        return userRepository.existsByEmail(email);

    }

}
