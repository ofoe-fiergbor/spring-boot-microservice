package com.example.ratingsdataservice.resources;

import com.example.ratingsdataservice.models.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 5);
    }
}
