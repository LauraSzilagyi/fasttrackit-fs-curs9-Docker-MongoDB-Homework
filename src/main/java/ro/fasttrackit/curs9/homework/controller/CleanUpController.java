package ro.fasttrackit.curs9.homework.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs9.homework.entity.CleanUp;
import ro.fasttrackit.curs9.homework.model.CleanUpModel;
import ro.fasttrackit.curs9.homework.service.CleanUpService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("rooms/{roomId}/cleanups")
public class CleanUpController {
    private final CleanUpService service;

    @GetMapping
    List<CleanUp> getCleanUpsByRoomId(@PathVariable String roomId) {
        return service.getCleanUpsByRoom(roomId);
    }

    @PostMapping
    CleanUp addNewCleanUpToRoom(@PathVariable String roomId, @RequestBody CleanUpModel model) {
        return service.addNewCleanUpToRoom(roomId, model);
    }

    @PatchMapping
    List<CleanUp> updateCleanUp(@PathVariable String roomId, @RequestBody JsonPatch jsonPatch) {
        return service.updateCleanUp(roomId, jsonPatch);
    }

    @DeleteMapping("{cleanUpId}")
    CleanUp deleteCleanUp(@PathVariable String roomId, @PathVariable String cleanUpId) {
        return service.deleteCleanUp(roomId, cleanUpId).orElse(null);
    }
}
