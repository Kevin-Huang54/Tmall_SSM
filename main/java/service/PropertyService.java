package service;

import pojo.Property;

import java.util.List;

//属性的基本增删改查
public interface PropertyService {
    void add(Property property);
    void delete(int id);
    void update(Property property);
    Property get(int id);
    List<Property> list(int cid);
}