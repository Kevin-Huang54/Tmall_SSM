package mapper;

import pojo.Property;
import pojo.PropertyValue;

import java.util.List;

public interface PropertyValueMapper {
    void add(PropertyValue propertyValue);
    void delete(int id);
    void update(PropertyValue propertyValue);
    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}
