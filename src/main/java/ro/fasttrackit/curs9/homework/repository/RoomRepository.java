package ro.fasttrackit.curs9.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.curs9.homework.entity.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
}
