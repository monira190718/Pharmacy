package com.sparktech.finalproject.registration;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {


        User user = userRepository.findByEmail(email)

                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                );
        System.out.println("LOGIN TRY: " + email);
        System.out.println("USER ROLE: " + user.getRole());

        return new CustomUserDetails(user);

    }

}