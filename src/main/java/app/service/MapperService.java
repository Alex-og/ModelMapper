package app.service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Oleksandr Haleta
 */
public interface MapperService {
    void bind() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException;
}
