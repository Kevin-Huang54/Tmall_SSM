package service.impl;

import mapper.PropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Property;
import service.PropertyService;

import java.util.List;

@Service
//属性的基本增删改查
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;

    @Override
    public void add(Property property) {
        propertyMapper.add(property);
    }

    @Override
    public void delete(int id) {
        propertyMapper.delete(id);
    }

    @Override
    public void update(Property property) {
        propertyMapper.update(property);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.get(id);
    }

    @Override
    public List<Property> list(int cid) {
        return propertyMapper.list(cid);
    }
}
