package springweb.backend.model;

import lombok.Builder;

import java.util.List;

@Builder
public record AppUser(
        String id,
        String username,
        String password,
        AppUserRole role,
        List<String> items
) {
}
