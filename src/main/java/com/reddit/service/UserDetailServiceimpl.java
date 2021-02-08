package com.reddit.service;

import com.reddit.exceptions.AuthRedditException;
import com.reddit.model.Usera;
import com.reddit.repository.UseraRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
@Service
@AllArgsConstructor
public class UserDetailServiceimpl implements UserDetailsService {

    private final UseraRepository useraRepository;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Usera> userOpt = useraRepository.findByUsername(userName);
        Usera user = userOpt.
                orElseThrow(
                            () -> new AuthRedditException("No user found or fail autheticate"));
        return new org.springframework.security.core.userdetails.
                User(user.getUsername(),user.getPassword(),
                user.isEnabled(),true,true,true,getAuthorization("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorization(String role){
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
