package springweb.backend.controller;

import com.cloudinary.api.exceptions.BadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @GetMapping("me")
    public AppUserResponse getCurrentUser(@AuthenticationPrincipal User user) {
        System.out.println(user);
        AppUser appUser = appUserService.getUserByUsername(user.getUsername());
        return new AppUserResponse(appUser.id(), appUser.username(), appUser.role());
    }

    @PostMapping
    public AppUserResponse register(@RequestBody AppUserRegisterDto appUserRegisterDto) throws BadRequest {
        if(appUserService.userExistsByUsername(appUserRegisterDto.username())){
            throw new BadRequest("Username already exists");
        }
        else{
            AppUser appUser = appUserService.addUser(appUserRegisterDto);
            return new AppUserResponse(appUser.id(), appUser.username(), appUser.role());
        }
    }


}
