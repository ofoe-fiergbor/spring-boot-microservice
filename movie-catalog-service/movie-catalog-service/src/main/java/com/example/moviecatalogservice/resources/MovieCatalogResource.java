package com.example.moviecatalogservice.resources;

import com.example.moviecatalogservice.models.CatalogItem;
import com.example.moviecatalogservice.models.Movie;
import com.example.moviecatalogservice.models.Rating;
import com.example.moviecatalogservice.models.UserRating;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;
//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

        //get all rated movie ids
        UserRating ratings = restTemplate.getForObject("http://RATINGS-DATA-SERVICE/ratingsdata/users/"+ userId, UserRating.class);
        //for each movie id, call movie info service and get details
        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/"+ rating.getMovieId(), Movie.class);
//            Movie movie = webClientBuilder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/"+ rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class).block();
            return new CatalogItem(movie.getName(), "Test Description", rating.getRating());
        }).collect(Collectors.toList());

        //put them all together

    }
}
