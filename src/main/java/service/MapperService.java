package service;

import utils.Pair;
import utils.Parser;

public class MapperService<U, T> {

    private Parser parser;

    public MapperService() {
        parser = new Parser();
    }

    @SuppressWarnings("unchecked")
    public T map(U u, Class<T> destinationType) {
        T t = (T)this.bind(u, destinationType);
        return t;
    }

    private Object bind(U u, Class<T> destinationType) {
        Pair[] pairs = parser.getFields();
        return null;
    }
}
