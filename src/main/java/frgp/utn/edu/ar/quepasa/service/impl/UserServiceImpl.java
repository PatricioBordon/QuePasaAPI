package frgp.utn.edu.ar.quepasa.service.impl;


import frgp.utn.edu.ar.quepasa.data.request.user.UserPatchEditRequest;
import frgp.utn.edu.ar.quepasa.model.User;
import frgp.utn.edu.ar.quepasa.repository.UserRepository;
import frgp.utn.edu.ar.quepasa.service.Auth;
import frgp.utn.edu.ar.quepasa.service.AuthenticationService;
import frgp.utn.edu.ar.quepasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired @Lazy
    private AuthenticationService authenticationService;
    @Autowired
    private Auth auth;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    public User update(String username, UserPatchEditRequest newUser) {
        User user = findByUsername(username);
        if(newUser.getName() != null) user.setName(newUser.getName());
        if(newUser.getAddress() != null) user.setAddress(newUser.getAddress());
        if(newUser.getNeighbourhood() != null) user.setNeighbourhood(newUser.getNeighbourhood());
        if(newUser.getPicture() != null) user.setProfilePicture(newUser.getPicture());
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(UserPatchEditRequest newUser) {
        User user = authenticationService.getCurrentUserOrDie();
        if(newUser.getName() != null) user.setName(newUser.getName());
        if(newUser.getAddress() != null) user.setAddress(newUser.getAddress());
        if(newUser.getNeighbourhood() != null) user.setNeighbourhood(newUser.getNeighbourhood());
        if(newUser.getPicture() != null) user.setProfilePicture(newUser.getPicture());
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(String username) {
        User user = findByUsername(username);
        user.setActive(false);
        userRepository.save(user);
    }
}
