package app;

import app.service.MapperService;
import app.service.MapperServiceImpl;

import java.lang.reflect.InvocationTargetException;

public class ModelMapper {

    public<U, T> void map(U sourse, T target) {
        MapperService<U, T> mapperService = new MapperServiceImpl<>(sourse, target);
        try {
            mapperService.bind();
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
