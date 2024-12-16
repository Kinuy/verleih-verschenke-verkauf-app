package springweb.backend.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import springweb.backend.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
