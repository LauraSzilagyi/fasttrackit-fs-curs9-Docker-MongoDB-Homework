package ro.fasttrackit.curs9.homework.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Document("cleanups")
public record CleanUp(
        @Id
        String id,
        LocalDate date,
        String procedure,
        String roomId,
        CleaningProcedure cleaningProcedure) {
}
