package app.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Parser  {
    private Object source;

    public <T> void setSource(T source) {
        this.source = source;
    }

    public <T> Field[] getFields() {
        Class<?> sourceClass = source.getClass();
        return sourceClass.getDeclaredFields();
    }

    public<T> Method[] getSetters() {
        Method[] methods = getMethods();
        return Arrays.stream(methods).filter(this::isSetter).toArray(Method[]::new);
    }

    public<T> Method[] getGetters() {
        Method[] methods = getMethods();
        return Arrays.stream(methods).filter(this::isGetter).toArray(Method[]::new);
    }

    private <T> Method[] getMethods() {
        Class<?> sourceClass = source.getClass();
        return sourceClass.getDeclaredMethods();
    }

    private boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (void.class.equals(method.getReturnType())) {
            return false;
        }
        return true;
    }

    private boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }
}