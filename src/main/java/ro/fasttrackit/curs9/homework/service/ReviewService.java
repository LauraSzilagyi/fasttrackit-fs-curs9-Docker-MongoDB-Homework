package ro.fasttrackit.curs9.homework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs9.homework.entity.Review;
import ro.fasttrackit.curs9.homework.exceptions.EntityNotFoundException;
import ro.fasttrackit.curs9.homework.exceptions.InvalidReviewException;
import ro.fasttrackit.curs9.homework.model.ReviewModel;
import ro.fasttrackit.curs9.homework.repository.ReviewRepository;
import ro.fasttrackit.curs9.homework.repository.RoomRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final RoomRepository roomRepository;

    public List<Review> getReviewsByRoom(String roomId) {
        verifyIfRoomExists(roomId);
        return repository.findAllByRoomId(roomId);
    }

    private void verifyIfRoomExists(String roomId) {
        roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(roomId));
    }

    public Review addNewReview(String roomId, ReviewModel model) {
        verifyIfRoomExists(roomId);
        validateModel(model);
        Review review = Review.builder()
                .id(randomUUID().toString())
                .mesaj(model.mesaj())
                .rating(model.rating())
                .turist(model.turist())
                .roomId(roomId)
                .build();
        return repository.save(review);
    }

    private void validateModel(ReviewModel model) {
        if (model.rating() == null || model.turist() == null) {
            throw new InvalidReviewException("Review must contains rating and turist!");
        }
    }

    public List<Review> updateReview(String roomId, JsonPatch jsonPatch) {
        verifyIfRoomExists(roomId);
        List<Review> reviews = repository.findAllByRoomId(roomId);
        List<Review> pachedReviews = applyPatch(reviews, jsonPatch);
        return repository.saveAll(pachedReviews);
    }

    private List<Review> applyPatch(List<Review> dbEntity, JsonPatch jsonPatch) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.findAndRegisterModules();
            JsonNode jsonNode = jsonMapper.convertValue(dbEntity, JsonNode.class);
            JsonNode patchedJson = jsonPatch.apply(jsonNode);
            Review[] reviews = jsonMapper.treeToValue(patchedJson, Review[].class);
            return Arrays.asList(reviews);
        } catch (JsonProcessingException | JsonPatchException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Review> deleteReview(String roomId, String id) {
        verifyIfRoomExists(roomId);
        Optional<Review> review = repository.findById(id);
        review.ifPresent(repository::delete);
        return review;
    }
}
