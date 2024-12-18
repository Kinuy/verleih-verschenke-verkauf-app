package springweb.backend.controller;

import com.cloudinary.api.exceptions.BadRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import springweb.backend.model.AppUser;
import springweb.backend.model.AppUserResponse;
import springweb.backend.model.AppUserRegisterDto;
import springweb.backend.service.AppUserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/me")
    public AppUserResponse getCurrentUser(@AuthenticationPrincipal User user){
            AppUser appUser = appUserService.getUserByUsername(user.getUsername());
            return new AppUserResponse(appUser.id(), appUser.username(), appUser.role(),appUser.items());
    }

    @PostMapping("/login")
    public AppUserResponse login(@AuthenticationPrincipal User user){
        return appUserService.getLoggedInUser(user);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserResponse register(@RequestBody AppUserRegisterDto appUserRegisterDto) throws BadRequest {
        if(appUserService.userExistsByUsername(appUserRegisterDto.username())){
            throw new BadRequest("Username already exists");
        }
        else{
            AppUser appUser = appUserService.register(appUserRegisterDto);
            return new AppUserResponse(appUser.id(), appUser.username(), appUser.role(),appUser.items());
        }
    }

    @GetMapping("csrf")
    public CsrfToken getCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }

//    @PostMapping("/logout")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void logout(HttpSession session) {
//        session.invalidate();
//        SecurityContextHolder.clearContext();
//    }


}
