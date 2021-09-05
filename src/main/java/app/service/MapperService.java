package app.service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Oleksandr Haleta
 */
public interface MapperService<U, T> {
    void bind() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException;
}
