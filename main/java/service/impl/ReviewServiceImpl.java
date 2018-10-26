package service.impl;

import mapper.ReviewMapper;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Review;
import service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<Review> list(int pid) {
        List<Review> reviews = reviewMapper.list(pid);
        for (Review review : reviews) {
            int uid = review.getUid();
            review.setUser(userMapper.get(uid));
        }
        return reviews;
    }

    @Override
    public void add(Review review) {
        reviewMapper.add(review);
    }

    @Override
    public void delete(int id) {
        reviewMapper.delete(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.update(review);
    }

    @Override
    public Review get(int id) {
        Review review = reviewMapper.get(id);
        int uid = review.getUid();
        review.setUser(userMapper.get(uid));
        return review;
    }

    @Override
    public int getCount(int pid) {
        return reviewMapper.getCount(pid);
    }
}
