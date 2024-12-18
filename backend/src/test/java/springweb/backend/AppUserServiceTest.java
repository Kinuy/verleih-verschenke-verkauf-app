package springweb.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import springweb.backend.model.AppUser;
import springweb.backend.model.AppUserRegisterDto;
import springweb.backend.model.AppUserResponse;
import springweb.backend.model.AppUserRole;
import springweb.backend.repository.AppUserRepository;
import springweb.backend.service.AppUserService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private AppUserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AppUserRegisterDto appUserRegisterDto;
    private AppUser appUser;

    private User mockUser;

    @BeforeEach
    void setUp() {
        // Initialize mocks and the test data
        MockitoAnnotations.openMocks(this);

        appUserRegisterDto = new AppUserRegisterDto("testuser", "password");
        appUser = new AppUser("1L", "testuser", "encodedPassword", AppUserRole.USER, null);

        mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("testuser");

    }

    @Test
    void testAddUser() {
        // Arrange
        when(passwordEncoder.encode(appUserRegisterDto.password())).thenReturn("encodedPassword");
        when(userRepo.save(any(AppUser.class))).thenReturn(appUser);

        // Act
        AppUser savedUser = appUserService.register(appUserRegisterDto);

        // Assert
        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.username());
        assertEquals("encodedPassword", savedUser.password());
        assertEquals(AppUserRole.USER, savedUser.role());

        // Verify interactions
        verify(passwordEncoder, times(1)).encode(appUserRegisterDto.password());
        verify(userRepo, times(1)).save(any(AppUser.class));
    }

    @Test
    void testGetUserByUsername_UserExists() {
        // Arrange
        when(userRepo.findAppUserByUsername("testuser")).thenReturn(Optional.of(appUser));

        // Act
        AppUser foundUser = appUserService.getUserByUsername("testuser");

        // Assert
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.username());
        assertEquals(AppUserRole.USER, foundUser.role());

        // Verify interaction
        verify(userRepo, times(1)).findAppUserByUsername("testuser");
    }

    @Test
    void testGetUserByUsername_UserDoesNotExist() {
        // Arrange
        when(userRepo.findAppUserByUsername("nonexistentuser")).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            appUserService.getUserByUsername("nonexistentuser");
        });

        assertEquals("User not found.", thrown.getMessage());

        // Verify interaction
        verify(userRepo, times(1)).findAppUserByUsername("nonexistentuser");
    }

    @Test
    void testUserExistsByUsername_UserExists() {
        // Arrange
        when(userRepo.existsByUsername("testuser")).thenReturn(true);

        // Act
        boolean exists = appUserService.userExistsByUsername("testuser");

        // Assert
        assertTrue(exists);

        // Verify interaction
        verify(userRepo, times(1)).existsByUsername("testuser");
    }

    @Test
    void testUserExistsByUsername_UserDoesNotExist() {
        // Arrange
        when(userRepo.existsByUsername("nonexistentuser")).thenReturn(false);

        // Act
        boolean exists = appUserService.userExistsByUsername("nonexistentuser");

        // Assert
        assertFalse(exists);

        // Verify interaction
        verify(userRepo, times(1)).existsByUsername("nonexistentuser");
    }


    @Test
    void testGetLoggedInUser_Success() {
        // Arrange: Mock the getUserByUsername method to return an AppUser
        when(userRepo.findAppUserByUsername(anyString())).thenReturn(java.util.Optional.of(appUser));

        // Act: Call the method
        AppUserResponse response = appUserService.getLoggedInUser(mockUser);

        // Assert: Verify that the returned AppUserResponse contains the correct values
        assertNotNull(response);
        assertEquals(appUser.id(), response.id());
        assertEquals(appUser.username(), response.username());
        assertEquals(appUser.role(), response.role());
        assertEquals(appUser.items(), response.items());

        // Verify that the getUserByUsername method was called once with the correct username
        verify(userRepo, times(1)).findAppUserByUsername("testuser");
    }
}