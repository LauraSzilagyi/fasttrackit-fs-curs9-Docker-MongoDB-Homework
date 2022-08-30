package ro.fasttrackit.curs9.homework.loader;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.fasttrackit.curs9.homework.entity.*;
import ro.fasttrackit.curs9.homework.repository.CleanUpRepository;
import ro.fasttrackit.curs9.homework.repository.ReviewRepository;
import ro.fasttrackit.curs9.homework.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;

import static java.util.UUID.randomUUID;
import static ro.fasttrackit.curs9.homework.model.Outcome.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoomRepository roomRepository;
    private final CleanUpRepository cleanUpRepository;
    private final ReviewRepository reviewRepository;


    @Override

    public void run(String... args) throws Exception {
        List<Room> rooms = getRoomData();
        getCleanUpData(rooms);
        getReviewData(rooms);
    }

    private void getReviewData(List<Room> rooms) {
        reviewRepository.deleteAll();
        List.of(
                new Review(randomUUID().toString(),
                        "Good",
                        5,
                        "Kamilla",
                        rooms.get(0).id()),
                new Review(randomUUID().toString(),
                        "Not so good",
                        4,
                        "Andrei",
                        rooms.get(0).id()),
                new Review(randomUUID().toString(),
                        "Horible",
                        1,
                        "Andrea",
                        rooms.get(2).id())
        ).forEach(reviewRepository::save);
        System.out.println(reviewRepository.findAll());
    }

    private void getCleanUpData(List<Room> rooms) {
        cleanUpRepository.deleteAll();

        List.of(
                new CleanUp(randomUUID().toString(),
                        LocalDate.of(2022, 6, 12),
                        "big cleanup",
                        rooms.get(0).id(),
                        new CleaningProcedure("name1", DONE)),

                new CleanUp(randomUUID().toString(),
                        LocalDate.now(),
                        "fast cleanup",
                        rooms.get(0).id(),
                        new CleaningProcedure("name2", INPROGRESS)),
                new CleanUp(randomUUID().toString(),
                        LocalDate.of(2022, 7, 15),
                        "big cleanup",
                        rooms.get(2).id(),
                        new CleaningProcedure("name3", NOSTARTED))

        ).forEach(cleanUpRepository::save);

        System.out.println(cleanUpRepository.findAll());
    }

    private List<Room> getRoomData() {
        roomRepository.deleteAll();
        List.of(
                new Room(randomUUID().toString(), "13b", 2, "Hotel Marina", new RoomFacilities(true, true)),
                new Room(randomUUID().toString(), "5a", 1, "Hotel Marina", new RoomFacilities(true, true)),
                new Room(randomUUID().toString(), "9a", 1, "Hotel FiveStar", new RoomFacilities(false, true)),
                new Room(randomUUID().toString(), "2c", 3, "Hotel FiveStar", new RoomFacilities(true, false))
        ).forEach(roomRepository::save);

        List<Room> rooms = roomRepository.findAll();
        System.out.println(rooms);
        return rooms;
    }
}