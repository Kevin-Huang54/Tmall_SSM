package service.impl;

import mapper.PropertyMapper;
import mapper.PropertyValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Property;
import pojo.PropertyValue;
import service.PropertyValueService;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyMapper propertyMapper;
    
    @Override
    public void add(PropertyValue propertyValue) {
        propertyValueMapper.add(propertyValue);
    }

    @Override
    public void delete(int id) {
        propertyValueMapper.delete(id);
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.update(propertyValue);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValue propertyValue = propertyValueMapper.get(ptid, pid);
        if (null != propertyValue) {
            setProperty(propertyValue);
        }
        return propertyValue;
    }

    @Override
    public List<PropertyValue> list(int pid) {
        List<PropertyValue> propertyValues = propertyValueMapper.list(pid);
        for (PropertyValue propertyValue : propertyValues) {
            setProperty(propertyValue);
        }
        return propertyValues;
    }

    //填充propertyValue的property属性
    public void setProperty(PropertyValue propertyValue) {
        Property property = propertyMapper.get(propertyValue.getPtid());
        propertyValue.setProperty(property);
    }
}
