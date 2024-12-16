package springweb.backend.controller;

import com.cloudinary.api.exceptions.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import springweb.backend.model.AppUser;
import springweb.backend.model.AppUserProfile;
import springweb.backend.model.AppUserRegisterDto;
import springweb.backend.service.AppUserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("me")
    public AppUserProfile getCurrentUser(@AuthenticationPrincipal User user) {
        System.out.println(user);
        AppUser appUser = appUserService.getUserByUsername(user.getUsername());
        return new AppUserProfile(appUser.id(), appUser.username(), appUser.role());
    }

    @PostMapping
    public AppUserProfile register(@RequestBody AppUserRegisterDto appUserRegisterDto) throws BadRequest {
        if(appUserService.userExistsByUsername(appUserRegisterDto.username())){
            throw new BadRequest("Username already exists");
        }
        else{
            AppUser appUser = appUserService.addUser(appUserRegisterDto);
            return new AppUserProfile(appUser.id(), appUser.username(), appUser.role());
        }


    }
}
