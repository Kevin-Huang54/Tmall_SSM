package service.impl;

import mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;
import service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public void delete(int id) {
        userMapper.delete(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User get(int id) {
        return userMapper.get(id);
    }

    @Override
    public User getByNameAndPw(String name, String password) {
        return userMapper.getByNameAndPw(name, password);
    }

    @Override
    public boolean isExist(String name) {
        User user = userMapper.getByName(name);
        if (null == user) {
            return false;
        }
        return true;
    }
}
