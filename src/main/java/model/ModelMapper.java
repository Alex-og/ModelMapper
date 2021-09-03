package model;

import service.MapperService;

public class ModelMapper<U, T> {
    private MapperService<U, T> mapperService;

    public ModelMapper() {
        mapperService = new MapperService<>();
    }

    /*public OrderDTO map(Order order, Class clazz) {
            ModelMapper modelMapper = new ModelMapper();
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            return orderDTO;
        }*/
    @SuppressWarnings("unchecked")
    public T map(U u, Class<T> destinationType) {
        T t = (T)mapperService.map(u, destinationType);
        return t;
    }
}
