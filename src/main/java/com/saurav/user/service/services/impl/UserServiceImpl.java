package com.saurav.user.service.services.impl;

import com.saurav.user.service.entities.Hotel;
import com.saurav.user.service.entities.Rating;
import com.saurav.user.service.entities.User;
import com.saurav.user.service.exceptions.ResourceNotFoundException;
import com.saurav.user.service.repositories.UserRepository;
import com.saurav.user.service.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> all = userRepository.findAll();
        ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings", ArrayList.class);
        log.info("{}",ratingsOfUser);
        return all;
    }

    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id is not found on server !!: " + userId));
        //fetch rating of the above user from rating service
        Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/"+user.getUserId(), Rating[].class);
        log.info("ratings  {}",ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).collect(Collectors.toList());

        List<Rating> ratingList = ratings.stream().map(rating -> {
                    ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(), Hotel.class);
                    Hotel hotel = forEntity.getBody();
                    log.info("response status code: {}", forEntity.getStatusCode());
                    rating.setHotel(hotel);
                    return rating;
                }
        ).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }
}
