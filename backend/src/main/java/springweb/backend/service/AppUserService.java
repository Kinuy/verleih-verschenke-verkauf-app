package springweb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springweb.backend.model.AppUser;
import springweb.backend.model.AppUserRegisterDto;
import springweb.backend.model.AppUserResponse;
import springweb.backend.model.AppUserRole;
import springweb.backend.repository.AppUserRepository;

import java.util.Collections;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppUserService{

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;


    public AppUserResponse getLoggedInUser(User user) {
        AppUser appUser =  getUserByUsername(user.getUsername());
        return new AppUserResponse(appUser.id(),appUser.username(),appUser.role(),appUser.items());
    }

    public AppUser getUserByUsername(String username) {
        return userRepo.findAppUserByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found."));
    }

    public boolean userExistsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public AppUser register(AppUserRegisterDto appUserRegisterDto) {
        AppUser appUser = new AppUser(
                null,
                appUserRegisterDto.username(),
                passwordEncoder.encode(appUserRegisterDto.password()),
                AppUserRole.USER,
                Collections.emptyList()
        );
        return userRepo.save(appUser);
    }
}
