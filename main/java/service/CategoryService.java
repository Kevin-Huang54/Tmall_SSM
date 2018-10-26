package service;

import pojo.Category;
import util.Page;

import java.util.List;

public interface CategoryService {
    List<Category> list();

//    int total();

    void add(Category category);

    void delete(int id);

    void update(Category category);

    Category get(int id);
}
