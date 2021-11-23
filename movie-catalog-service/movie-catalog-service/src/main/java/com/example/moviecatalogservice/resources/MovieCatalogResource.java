package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        
//        Movie movie = restTemplate.getForObject("http://localhost:8082/movies/foo", Movie.class);

        //get all rated movie ids
        List<Rating> ratings = Arrays.asList(
                new Rating("foo", 4),
                new Rating("bar", 2)
        );
        //for each movie id, call movie info service and get details
        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+ rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test Description", rating.getRating());
        }).collect(Collectors.toList());

        //put them all together

    }
}
