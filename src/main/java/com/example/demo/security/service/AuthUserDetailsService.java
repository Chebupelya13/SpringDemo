package com.example.demo.security.service;

import com.example.demo.dao.UserDao;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public AuthUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserDetails userDetails = (UserDetails) userDao.getUserByUsername(username);
        if (userDetails != null)
            return userDetails;

        throw new UsernameNotFoundException("User not found " + username);
    }
}
