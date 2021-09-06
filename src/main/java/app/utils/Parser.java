package app.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Oleksandr Haleta
 */
public interface Parser {
    <T> void setSource(T source);

    <T> Field[] getFields();

    <T> Method[] getSetters();

    <T> Method[] getGetters();
}
