package ro.fasttrackit.curs9.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs9.homework.entity.CleanUp;
import ro.fasttrackit.curs9.homework.entity.Room;
import ro.fasttrackit.curs9.homework.exceptions.EntityNotFoundException;
import ro.fasttrackit.curs9.homework.exceptions.InvalidCleanUpModelException;
import ro.fasttrackit.curs9.homework.exceptions.JsonPatchCannotBeAppliedException;
import ro.fasttrackit.curs9.homework.model.CleanUpModel;
import ro.fasttrackit.curs9.homework.repository.CleanUpRepository;
import ro.fasttrackit.curs9.homework.repository.RoomRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class CleanUpService {
    private final CleanUpRepository repository;
    private final RoomRepository roomRepository;

    public List<CleanUp> getCleanUpsByRoom(String roomId) {
        verifyIfRoomExists(roomId);
        return repository.findAllByRoomId(roomId);
    }

    private void verifyIfRoomExists(String roomId) {
        Optional<Room> roomEntity = roomRepository.findById(roomId);
        if (roomEntity.isEmpty()) {
            throw new EntityNotFoundException(roomId);
        }
    }

    public CleanUp addNewCleanUpToRoom(String roomId, CleanUpModel model) {
        verifyIfRoomExists(roomId);
        validateModel(model);
        CleanUp entity = CleanUp.builder()
                .id(randomUUID().toString())
                .date(model.date())
                .procedure(model.procedure())
                .roomId(roomId)
                .cleaningProcedure(model.cleaningProcedure())
                .build();
        return repository.save(entity);
    }

    private void validateModel(CleanUpModel model) {
        if (model.date() == null || model.procedure() == null) {
            throw new InvalidCleanUpModelException("Must contains date and procedure!");
        }
    }

    public List<CleanUp> updateCleanUp(String roomId, JsonPatch jsonPatch) {
        verifyIfRoomExists(roomId);
        List<CleanUp> cleanUps = repository.findAllByRoomId(roomId);
        List<CleanUp> patchedCleanUps = applyPatch(cleanUps, jsonPatch);

        return repository.saveAll(patchedCleanUps);
    }

    private List<CleanUp> applyPatch(List<CleanUp> dbEntity, JsonPatch jsonPatch) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.findAndRegisterModules();
            JsonNode jsonNode = jsonMapper.convertValue(dbEntity, JsonNode.class);
            JsonNode patchedJson = jsonPatch.apply(jsonNode);
            CleanUp[] cleanUps = jsonMapper.treeToValue(patchedJson, CleanUp[].class);
            return Arrays.asList(cleanUps);
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new JsonPatchCannotBeAppliedException(e);
        }
    }

    public Optional<CleanUp> deleteCleanUp(String roomId, String id) {
        verifyIfRoomExists(roomId);
        Optional<CleanUp> cleanUp = repository.findById(id);
        cleanUp.ifPresent(repository::delete);
        return cleanUp;
    }
}
