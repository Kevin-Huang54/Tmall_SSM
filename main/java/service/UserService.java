package service;

import pojo.User;

import java.util.List;

public interface UserService {
    List<User> list();

    void add(User user);

    User get(int id);

    User getByNameAndPw(String name, String password);

    boolean isExist(String name);
}
