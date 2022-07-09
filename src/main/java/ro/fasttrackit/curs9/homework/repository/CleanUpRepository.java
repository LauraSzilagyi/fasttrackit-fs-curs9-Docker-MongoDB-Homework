package ro.fasttrackit.curs9.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.curs9.homework.entity.CleanUp;

import java.util.List;

public interface CleanUpRepository extends MongoRepository<CleanUp, String> {
    List<CleanUp> findAllByRoomId(String roomId);
}
