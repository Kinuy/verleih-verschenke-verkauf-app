package springweb.backend.model;

public record AppUserResponse(
        String id,
        String username,
        AppUserRole role
) {
}
