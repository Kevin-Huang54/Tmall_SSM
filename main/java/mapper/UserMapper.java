package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.User;

import java.util.List;

public interface UserMapper {

    int add(User user);

    void delete(int id);

    User get(int id);

    User getByName(String name);

    User getByNameAndPw(@Param("name")String name, @Param("password")String password);

    int update(User user);

    List<User> list();
}