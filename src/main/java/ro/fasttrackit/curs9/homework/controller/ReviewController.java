package ro.fasttrackit.curs9.homework.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs9.homework.entity.Review;
import ro.fasttrackit.curs9.homework.model.ReviewModel;
import ro.fasttrackit.curs9.homework.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("rooms/{roomId}/reviews")
public class ReviewController {
    private final ReviewService service;

    @GetMapping
    List<Review> getByRoom(@PathVariable String roomId) {
        return service.getReviewsByRoom(roomId);
    }

    @PostMapping
    Review addNewReview(@PathVariable String roomId, @RequestBody ReviewModel model) {
        return service.addNewReview(roomId, model);
    }

    @PatchMapping
    List<Review> updateReview(@PathVariable String roomId, @RequestBody JsonPatch jsonPatch) {
        return service.updateReview(roomId, jsonPatch);
    }

    @DeleteMapping("{id}")
    Review deleteReview(@PathVariable String roomId, @PathVariable String id) {
        return service.deleteReview(roomId, id).orElse(null);
    }

}
