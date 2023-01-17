package com.example.authservice.app.service;

import com.example.authservice.app.model.User;
import com.example.authservice.app.repository.UserRepository;
import com.example.authservice.app.requestBeans.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        if (!userRepository.findByMail(user.getMail()).isPresent()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        //todo add Exception
    }

    @Override
    public User findByMail(String mail) throws UsernameNotFoundException{
        User user = userRepository.findByMail(mail).orElse(null);
        if (user != null){
            return user;
        }
        throw new UsernameNotFoundException("Пользователь " + mail + " не зарегистрирован.");
    }

    @Override
    public void updateUser(UserRequest dto) {
            User user = userRepository.findByMail(dto.getMail()).orElseThrow(() -> new RuntimeException("User not found"));
            if (user != null){
                user.setFirstName(dto.getFirstName());
                user.setLastName(dto.getLastName());
                System.out.println("dto -> " + user);
                userRepository.save(user);
            }
    }
}
