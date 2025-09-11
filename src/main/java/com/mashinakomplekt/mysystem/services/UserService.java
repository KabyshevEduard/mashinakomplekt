package com.mashinakomplekt.mysystem.services;


import com.mashinakomplekt.mysystem.dao.RoleRepository;
import com.mashinakomplekt.mysystem.dao.UserRepository;
import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.dto.UserDto.RegistartionUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = findByUsername(username);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getKind())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistartionUserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(List.of(roleRepository.findBykind("ROLE_USER").get()));
        userRepository.save(user);
        return user;
    }

    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }
}
