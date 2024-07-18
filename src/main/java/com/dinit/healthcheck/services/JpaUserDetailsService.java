package com.dinit.healthcheck.services;

import com.dinit.healthcheck.dtos.UserAddDto;
import com.dinit.healthcheck.dtos.UserGetDto;
import com.dinit.healthcheck.models.SecurityUser;
import com.dinit.healthcheck.models.User;
import com.dinit.healthcheck.repositorys.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameWithRole(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public UserGetDto authenticateUser(UserAddDto userAddDto) {
        Optional<User> user = userRepository.findByUsernameWithRole(userAddDto.getUsername());
        if (user.isEmpty())
            throw new UsernameNotFoundException("Username not found");
        if (passwordEncoder.matches(userAddDto.getPassword(), user.get().getPassword()))
            return UserGetDto.toDto(user.get());
        throw new UsernameNotFoundException("Wrong password");
    }
}
