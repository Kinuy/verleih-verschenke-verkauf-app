package springweb.backend.model;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ItemDto (
        String name,
        String img,
        String description,
        ItemCategory category,
        ItemStatus status
) {
}