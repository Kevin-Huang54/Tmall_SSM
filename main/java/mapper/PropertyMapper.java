package mapper;

import pojo.Property;

import java.util.List;

public interface PropertyMapper {
    void add(Property property);
    void delete(int id);
    void update(Property property);
    Property get(int id);
    List<Property> list(int cid);
}
