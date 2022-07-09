package ro.fasttrackit.curs9.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs9.homework.entity.Room;
import ro.fasttrackit.curs9.homework.exceptions.EntityNotFoundException;
import ro.fasttrackit.curs9.homework.filter.RoomFilter;
import ro.fasttrackit.curs9.homework.repository.RoomDao;
import ro.fasttrackit.curs9.homework.repository.RoomRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;
    private final RoomDao dao;

    public Page<Room> getAll(RoomFilter filters, Pageable pageable) {
        return dao.findBy(filters, pageable);
    }

    public Optional<Room> getRoomById(String id) {
        return repository.findById(id);
    }

    public Room updateRoom(String id, JsonPatch jsonPatch) {
        Room room = repository.findById(id)
                .map(dbEntity -> applyPatch(dbEntity, jsonPatch))
                .orElseThrow(() -> new EntityNotFoundException(id));

        return repository.save(room);

    }

    private Room applyPatch(Room dbEntity, JsonPatch jsonPatch) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.findAndRegisterModules();
            JsonNode jsonNode = jsonMapper.convertValue(dbEntity, JsonNode.class);
            JsonNode patchedJson = jsonPatch.apply(jsonNode);
            return jsonMapper.treeToValue(patchedJson, Room.class);
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Room> deleteRoomById(String id) {
        Optional<Room> room = repository.findById(id);
        room.ifPresent(repository::delete);
        return room;
    }
}
