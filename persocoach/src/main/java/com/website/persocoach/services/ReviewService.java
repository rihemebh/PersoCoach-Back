package com.website.persocoach.services;

import com.website.persocoach.Models.Coach;
import com.website.persocoach.Models.Review;
import com.website.persocoach.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired ReviewRepository reviewRepository;

    public List<Review> findAllByCoach(Coach coach){
        return reviewRepository.findAllByCoach(coach);
    }

    public Review save(Review review){ return reviewRepository.save(review);}

    public void deleteById(String id){ reviewRepository.deleteById(id);}
}
