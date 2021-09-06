package app;

import app.service.MapperService;
import app.service.MapperServiceImpl;

import java.lang.reflect.InvocationTargetException;

public class ModelMapper {

    public<U, T> void map(U source, T target) {
        if (source == null) throw new NullPointerException("The field 'source' cannot be null!");
        if (target == null) throw new NullPointerException("The field 'target' cannot be null!");
        MapperService<U, T> mapperService = new MapperServiceImpl<>(source, target);
        try {
            mapperService.bind();
        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
