package springweb.backend.model;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("items")
@With
@Builder
public record Item(
        @Id String id,
        String name,
        String img,
        String description,
        ItemCategory category,
        ItemStatus status,
        List<Double> geocode,
        String owner
) {
}
