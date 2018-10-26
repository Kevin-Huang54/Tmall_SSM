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
    //注册用户后添加
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public User get(int id) {
        return userMapper.get(id);
    }

    @Override
    //核对用户的密码
    public User getByNameAndPw(String name, String password) {
        return userMapper.getByNameAndPw(name, password);
    }

    @Override
    //查看用户名是否存在
    public boolean isExist(String name) {
        User user = userMapper.getByName(name);
        if (null == user) {
            return false;
        }
        return true;
    }
}
