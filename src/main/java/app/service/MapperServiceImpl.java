package app.service;

import app.annotations.Exclude;
import app.annotations.InstanceField;
import app.utils.Parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class MapperServiceImpl<U, T> implements MapperService<U, T> {
    private final U source;
    private final T target;

    private final Parser parser;

    public MapperServiceImpl(U source, T target) {
        this.source = source;
        this.target = target;
        this.parser = new Parser();
    }

    @Override
    public void bind() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method[] sourceGetters = getGetters(source);
        Method[] targetSetters = getSetters(target);
        if (sourceGetters.length > 0 && targetSetters.length > 0) {
            delegateDataToTargetSetters(sourceGetters, targetSetters);
        } else {
            delegateDataToTargetFields();
        }
    }

    private void delegateDataToTargetFields() throws NoSuchFieldException, IllegalAccessException {
        Field[] sourceFields = getFields(source);
        Field[] targetFields = getFields(target);
        if (sourceFields.length > 0 && targetFields.length > 0) {
            for (Field field : sourceFields) {
                if (hasInstanceFieldAnnotationField(field) && !hasExcludeAnnotationField(field)) {
                    Field targetField = target.getClass().getDeclaredField(field.getName());
                    Field sourceField = source.getClass().getDeclaredField(field.getName());
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    targetField.set(target, sourceField.get(source));
                }
            }
        }
    }

    private void delegateDataToTargetSetters(Method[] sourceGetters, Method[] targetSetters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        for (Method sourceGetter : sourceGetters) {
            if (hasInstanceFieldAnnotation(sourceGetter) && !hasExcludeAnnotation(sourceGetter)) {
                Method targetSetter = findMatchSetter(sourceGetter.getName(), targetSetters)
                        .orElseThrow(NoSuchMethodException::new);
                sourceGetter.setAccessible(true);
                targetSetter.setAccessible(true);
                targetSetter.invoke(target, sourceGetter.invoke(source, null));
            }
        }
    }

    private  <V> Method[] getGetters(V v) {
        parser.setSource(v);
        return parser.getGetters();
    }

    private  <V> Method[] getSetters(V v) {
        parser.setSource(v);
        return parser.getSetters();
    }

    private  <V> Field[] getFields(V v) {
        parser.setSource(v);
        return parser.getFields();
    }

    private Optional<Method> findMatchSetter(String getterName, Method[] targetSetters) {
        String s = getterName.substring(3);
        return Arrays.stream(targetSetters)
                .filter(x -> x.getName().substring(3).equals(s)).findFirst();
    }

    private boolean hasInstanceFieldAnnotationField(Field sourceField) {
        Annotation[] annotations = sourceField.getDeclaredAnnotations();
        return Arrays.stream(annotations).anyMatch(InstanceField.class::isInstance);
    }

    private boolean hasExcludeAnnotationField(Field sourceField) {
        Annotation[] annotations = sourceField.getDeclaredAnnotations();
        return Arrays.stream(annotations).anyMatch(Exclude.class::isInstance);
    }

    private boolean hasExcludeAnnotation(Method getter) {
        Annotation[] annotations = getter.getDeclaredAnnotations();
        return Arrays.stream(annotations).anyMatch(Exclude.class::isInstance);
    }

    private boolean hasInstanceFieldAnnotation(Method getter) {
        Annotation[] annotations = getter.getDeclaredAnnotations();
        return Arrays.stream(annotations).anyMatch(InstanceField.class::isInstance);
    }
}
