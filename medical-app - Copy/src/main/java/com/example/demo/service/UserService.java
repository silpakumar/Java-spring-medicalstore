package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

   
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet(Arrays.asList(userRole)));
        userRepository.save(user);
    }
	
//	
//	public void saveUserAsAdmin(User user) {
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role adminRole = roleRepository.findByRole("ADMIN");
//        user.setRoles(new HashSet<>(Arrays.asList(adminRole)));
//        userRepository.save(user);
//	}
	
//	public Boolean checkPassword(User user, String password) {
//		return bCryptPasswordEncoder.matches(password, user.getPassword());
//	}

}