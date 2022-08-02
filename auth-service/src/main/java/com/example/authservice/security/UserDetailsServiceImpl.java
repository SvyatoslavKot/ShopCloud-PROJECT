package com.example.authservice.security;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {
        return userRepository.findByMail(eMail)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getMail(),
                        user.getPassword(),
                        user.getRole().getAuthority()
                )).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
    }
}
