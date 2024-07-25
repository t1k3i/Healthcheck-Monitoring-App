package com.dinit.healthcheck.services;

import com.dinit.healthcheck.dtos.UserAddDto;
import com.dinit.healthcheck.dtos.UserGetDto;
import com.dinit.healthcheck.dtos.UserRegisterDto;
import com.dinit.healthcheck.exceptions.conflict.UsernameConflictException;
import com.dinit.healthcheck.exceptions.notfound.RoleNotFoundException;
import com.dinit.healthcheck.exceptions.notfound.UserNotFoundException;
import com.dinit.healthcheck.models.Role;
import com.dinit.healthcheck.models.SecurityUser;
import com.dinit.healthcheck.models.User;
import com.dinit.healthcheck.repositories.RoleRepository;
import com.dinit.healthcheck.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public void registerUser(UserRegisterDto userRegisterDto) {
        String roleName = userRegisterDto.getRole();
        Role role = roleRepository.findByName(roleName).orElse(null);
        if (role == null)
            throw new RoleNotFoundException();
        if (userRepository.existsByUsername(userRegisterDto.getUsername()))
            throw new UsernameConflictException();
        User user = UserRegisterDto.toUser(userRegisterDto, role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long urlId) {
        if (!userRepository.existsById(urlId))
            throw new UserNotFoundException();
        userRepository.deleteById(urlId);
    }

    public List<UserGetDto> getUsers(String currentUser) {
        List<UserGetDto> users = new ArrayList<>();
        for (User user : userRepository.findAllWithRole())
            if(!user.getUsername().equals(currentUser))
                users.add(UserGetDto.toDto(user));
        return users;
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
