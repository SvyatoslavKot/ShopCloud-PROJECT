package com.example.shop_module.service;

import com.example.shop_module.domain.Role;
import com.example.shop_module.domain.User;
import com.example.shop_module.dto.UserDTO;
import com.example.shop_module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAllUserDto() {
        return  userRepository.findAll().stream().map(
                user -> new UserDTO(
                      user.getName(),
                        user.getMail()
                )).collect(Collectors.toList());
    }

    @Override
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(),userDTO.getMatchingPassword())){
            throw new RuntimeException("Password is not equals!");
        }
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .mail(userDTO.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User finByMail(String mail) {
        return userRepository.findByMail(mail).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public void updateProfile(UserDTO userDTO) {
        User saveUser = userRepository.findByMail(userDTO.getEmail()).get();
        if (saveUser == null){
            throw  new RuntimeException("User not found by name: " + userDTO.getEmail());
        }
        boolean isChange = false;
        if(userDTO.getPassword()!= null && !userDTO.getPassword().isEmpty()) {
            saveUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            isChange = true;
        }
        if (isChange) {
            userRepository.save(saveUser);
        }
    }
}