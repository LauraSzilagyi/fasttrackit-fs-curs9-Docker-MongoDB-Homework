package ro.fasttrackit.curs9.homework.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("reviews")
public record Review(
        @Id
        String id,
        String mesaj,
        Integer rating,
        String turist,
        String roomId) {
}
