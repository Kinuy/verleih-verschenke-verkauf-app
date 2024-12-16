package springweb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springweb.backend.model.AppUser;
import springweb.backend.model.AppUserRegisterDto;
import springweb.backend.repository.AppUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppUserService{

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AppUser getUserById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id: " + id + " does not exist!"));
    }

    public AppUser addUser(AppUserRegisterDto appUserRegisterDto) {
        AppUser appUser = new AppUser(null,
                appUserRegisterDto.username(),
                passwordEncoder.encode(appUserRegisterDto.password()),
                "USER",
                Collections.emptyList()
        );
        return userRepo.save(appUser);
    }

    public AppUser getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User with username: " + username + " does not exist!"));
    }

    public boolean userExistsByUsername(String username) {

        return userRepo.existsByUsername(username);
    }
}
