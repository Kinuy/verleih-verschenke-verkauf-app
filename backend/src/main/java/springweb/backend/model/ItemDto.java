package springweb.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record ItemDto (
        String name,
        String img,
        String description,
        ItemCategory category,
        ItemStatus status,
        List<Double> geocode,
        String owner
) {
}