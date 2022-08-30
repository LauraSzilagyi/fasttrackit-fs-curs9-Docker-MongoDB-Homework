package ro.fasttrackit.curs9.homework.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "rooms")
public record Room(
        @Id
        String id,
        String number,
        int floor,
        String hotelName,
        RoomFacilities facilities
) {
}
