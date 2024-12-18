package springweb.backend.model;

import java.util.List;

public record AppUserResponse(
        String id,
        String username,
        AppUserRole role,
        List<String> items
) {
}
