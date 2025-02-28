package com.example.shop.config;

import org.modelmapper.ModelMapper;

public class CustomModelMapper extends ModelMapper {

    //파라미터 null값 처리

    @Override
    public <D> D map(Object source, Class<D> destinationType) {

        if(source == null) {
            return null;
        } else {
            return super.map(source, destinationType);
        }
    }
}
