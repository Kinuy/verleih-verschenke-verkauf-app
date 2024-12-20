package springweb.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springweb.backend.model.*;
import springweb.backend.repository.AppUserRepository;
import springweb.backend.service.AppUserService;


import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc

class AppUserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRepository userRepo;

    private AppUser appUser;

    @BeforeEach
    public void setup() {

        appUser = new AppUser(
                "1L",
                "testuser",
                "password",
                AppUserRole.USER,
                null);
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testGetCurrentUser() throws Exception {
        // Arrange: Mock the service method

        userRepo.save(appUser);
        // Act & Assert: Perform the GET request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/me")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                "username": "testuser",
                "role": "USER"
                }
                """));

        // Verify the service method was called once
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        // Arrange: Prepare the DTO and mock the necessary methods
        AppUserRegisterDto registerDto = new AppUserRegisterDto("newuser", "password");
        AppUser newUser = new AppUser("2L", "newuser", "encodedPassword", AppUserRole.USER, null);


        // Act & Assert: Perform the POST request and validate the response
        mockMvc.perform(post("/api/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                        "username":"newuser",
                        "password":"password"
                        }
                        
                        """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            {
                                "username": "newuser",
                                "role": "USER"
                            }
                        """))
                .andExpect(jsonPath("$.id").isNotEmpty());

        // Verify the service methods
    }

    @Test
    @WithMockUser(username = "testuser") // Simulate a logged-in user with username "testuser"
    void testLogin_Success() throws Exception {
        // Arrange: Mock the service method call
        AppUserResponse mockResponse = new AppUserResponse("1L", "testuser", AppUserRole.USER, Collections.emptyList());

        //when(appUserService.getLoggedInUser(any(User.class))).thenReturn(mockResponse);
        userRepo.save(appUser);
        // Act & Assert: Perform the POST request and verify the response
        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Check if the response status is OK (200)
                .andExpect(content().json("""
                            {
                                "id": "1L",
                                "username": "testuser",
                                "role": "USER",
                                "items": null
                            }
                        """));
        // Verify that the service method was called with the correct user
        //verify(appUserService, times(1)).getLoggedInUser(any(User.class));
    }

//    @Test
//    void testLogin_UnauthorizedUser() throws Exception {
//        // Arrange: Simulate a scenario where no user is authenticated
//        mockMvc.perform(post("/api/users/login")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden()); // Expect a 401 Unauthorized status
//    }

/*    @Test
    public void testRegisterUser_Failure_UsernameAlreadyExists() throws Exception {
        // Arrange: Mock the userExistsByUsername to return true
        AppUserRegisterDto registerDto = new AppUserRegisterDto("existinguser", "password");
        appUserService.addUser(registerDto);
        when(appUserService.userExistsByUsername("existinguser")).thenReturn(true);

        // Act & Assert: Perform the POST request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
{
"username": "existinguser",
"password": "password"
}
"""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already exists"));

        // Verify the service method
        verify(appUserService, times(1)).userExistsByUsername("existinguser");
    }*/
}