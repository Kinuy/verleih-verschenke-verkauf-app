package springweb.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import springweb.backend.model.Item;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}
