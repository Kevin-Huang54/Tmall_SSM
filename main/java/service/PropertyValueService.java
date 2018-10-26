package service;

import pojo.Product;
import pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    void add(PropertyValue propertyValue);
    void delete(int id);
    void update(PropertyValue propertyValue);
    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}