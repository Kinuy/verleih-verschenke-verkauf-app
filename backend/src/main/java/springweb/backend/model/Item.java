package springweb.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("items")
public record Item(
        String id,
        String name,
        String img,
        String description,
        ItemCategory category,
        ItemStatus status
) {
}
