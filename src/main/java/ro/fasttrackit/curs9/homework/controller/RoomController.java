package ro.fasttrackit.curs9.homework.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs9.homework.entity.Room;
import ro.fasttrackit.curs9.homework.exceptions.EntityNotFoundException;
import ro.fasttrackit.curs9.homework.filter.RoomFilter;
import ro.fasttrackit.curs9.homework.service.RoomService;

@RestController
@RequestMapping("rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @GetMapping
    Page<Room> getAll(RoomFilter filters, Pageable pageable) {
        return service.getAll(filters, pageable);
    }

    @GetMapping("{id}")
    Room getRoomById(@PathVariable String id) {
        return service.getRoomById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @PatchMapping("{id}")
    Room updateRoom(@PathVariable String id, @RequestBody JsonPatch jsonPatch) {
        return service.updateRoom(id, jsonPatch);
    }

    @DeleteMapping("{id}")
    Room deleteRoomById(@PathVariable String id) {
        return service.deleteRoomById(id).orElse(null);
    }
}
