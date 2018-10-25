package mapper;

import pojo.Review;

import java.util.List;

public interface ReviewMapper {
    List<Review> list(int pid);

    void add(Review review);

    void delete(int id);

    void update(Review review);

    Review get(int id);

    int getCount(int pid);
}
