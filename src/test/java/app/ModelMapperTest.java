package app;

import app.annotations.Exclude;
import app.annotations.InstanceField;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ModelMapperTest {

    public static class Order {
        @InstanceField("address")
        private String address;
        @InstanceField("items")
        private List<String> items;

        @Exclude
        private String exclude;

        public Order(String address) {
            this.address = address;
            items = List.of("item1", "item2");
        }

        @InstanceField("address")
        public String getAddress() {
            return address;
        }

        @InstanceField("address")
        public void setAddress(String address) {
            this.address = address;
        }

        @InstanceField("items")
        public List<String> getItems() {
            return items;
        }

        @InstanceField("items")
        public void setItems(List<String> items) {
            this.items = items;
            exclude = "Exclude";
        }

        @Exclude
        @InstanceField("exclude")
        public String getExclude() {
            return exclude;
        }

        @Exclude
        @InstanceField("exclude")
        public void setExclude(String exclude) {
            this.exclude = exclude;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "address='" + address + '\'' +
                    ", items=" + items +
                    ", exclude='" + exclude + '\'' +
                    '}';
        }
    }

    public static class OrderDTO {
        @InstanceField("address")
        private String address;
        @InstanceField("items")
        private List<String> items;

        @Exclude
        private String exclude;

        public OrderDTO(String address) {
            this.address = address;
            items = List.of("itemDto", "item4");
            exclude = "ExcludeDto";
        }

        @InstanceField("address")
        public String getAddress() {
            return address;
        }

        @InstanceField("address")
        public void setAddress(String address) {
            this.address = address;
        }

        @InstanceField("items")
        public List<String> getItems() {
            return items;
        }

        @InstanceField("items")
        public void setItems(List<String> items) {
            this.items = items;
        }

        @Exclude
        @InstanceField("exclude")
        public String getExclude() {
            return exclude;
        }

        @Exclude
        @InstanceField("exclude")
        public void setExclude(String exclude) {
            this.exclude = exclude;
        }

        @Override
        public String toString() {
            return "OrderDTO{" +
                    "address='" + address + '\'' +
                    ", items=" + items +
                    ", exclude='" + exclude + '\'' +
                    '}';
        }
    }

    public static class Order2 {
        @InstanceField("address")
        private String address;
        @InstanceField("items")
        private List<String> items;

        @Exclude
        private String exclude;

        public Order2(String address) {
            this.address = address;
            items = List.of("item1", "item2");
            exclude = "exclude2";
        }

        @Override
        public String toString() {
            return "Order2{" +
                    "address='" + address + '\'' +
                    ", items=" + items +
                    ", exclude='" + exclude + '\'' +
                    '}';
        }
    }

    public static class OrderDTO2 {
        @InstanceField("address")
        private String address;
        @InstanceField("items")
        private List<String> items;

        @Exclude
        private String exclude;

        public OrderDTO2(String address) {
            this.address = address;
            items = List.of("itemDto", "item4");
            exclude = "ExcludeDto2";
        }


        @Override
        public String toString() {
            return "OrderDTO2{" +
                    "address='" + address + '\'' +
                    ", items=" + items +
                    ", exclude='" + exclude + '\'' +
                    '}';
        }
    }

    @Test
    public void shouldDelegateDataToDTO() {
        ModelMapperTest.Order order = new ModelMapperTest.Order("Aaa");
        ModelMapperTest.OrderDTO orderDto = new ModelMapperTest.OrderDTO("Bbb");
        ModelMapper mapper = new ModelMapper();
        mapper.map(order, orderDto);
        assertSame(order.getAddress(), orderDto.getAddress());
        assertLinesMatch(order.getItems(), orderDto.getItems());
        assertNotEquals(order.getExclude(), orderDto.getExclude());
    }

    @Test
    public void shouldDelegateDataViceVersa() {
        ModelMapperTest.Order order = new ModelMapperTest.Order("Aaa");
        ModelMapperTest.OrderDTO orderDto = new ModelMapperTest.OrderDTO("Bbb");
        ModelMapper mapper = new ModelMapper();
        mapper.map(orderDto, order);
        assertSame(order.getAddress(), orderDto.getAddress());
        assertLinesMatch(order.getItems(), orderDto.getItems());
        assertNotEquals(order.getExclude(), orderDto.getExclude());
    }

    @Test
    public void shouldThrowNPEBecauseSourceNull() {
        ModelMapperTest.Order order = null;
        ModelMapperTest.OrderDTO orderDto = new ModelMapperTest.OrderDTO("Bbb");
        ModelMapper mapper = new ModelMapper();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> mapper.map(order, orderDto));
        assertEquals("The field 'source' cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldThrowNPEBecauseTargetNull() {
        ModelMapperTest.Order order = new ModelMapperTest.Order("Aaa");
        ModelMapperTest.OrderDTO orderDto = null;
        ModelMapper mapper = new ModelMapper();
        NullPointerException exception = assertThrows(NullPointerException.class, () -> mapper.map(order, orderDto));
        assertEquals("The field 'target' cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldFieldsDelegateNull() {
        ModelMapperTest.Order order = new ModelMapperTest.Order(null);
        ModelMapperTest.OrderDTO orderDto = new ModelMapperTest.OrderDTO("Bbb");
        ModelMapper mapper = new ModelMapper();
        mapper.map(order, orderDto);
        assertNull(order.getAddress(), orderDto.getAddress());
        assertLinesMatch(order.getItems(), orderDto.getItems());
        assertNotEquals(order.getExclude(), orderDto.getExclude());
    }

    @Test
    public void shouldFieldsDelegateNullViceVersa() {
        ModelMapperTest.Order order = new ModelMapperTest.Order("Aaa");
        ModelMapperTest.OrderDTO orderDto = new ModelMapperTest.OrderDTO(null);
        ModelMapper mapper = new ModelMapper();
        mapper.map(orderDto, order);
        assertNull(order.getAddress(), orderDto.getAddress());
        assertLinesMatch(order.getItems(), orderDto.getItems());
        assertNotEquals(order.getExclude(), orderDto.getExclude());
    }

    @Test
    public void shouldDelegateViaFields() {
        ModelMapperTest.Order2 order2 = new ModelMapperTest.Order2("Aaa");
        ModelMapperTest.OrderDTO2 orderDto2 = new ModelMapperTest.OrderDTO2("Bbb");
        ModelMapper mapper = new ModelMapper();
        mapper.map(order2, orderDto2);
        assertSame(order2.address, orderDto2.address);
        assertLinesMatch(order2.items, orderDto2.items);
        assertNotEquals(order2.exclude, orderDto2.exclude);
    }

    @Test
    public void shouldDelegateViaFieldsViceVersa() {
        ModelMapperTest.Order2 order2 = new ModelMapperTest.Order2("Aaa");
        ModelMapperTest.OrderDTO2 orderDto2 = new ModelMapperTest.OrderDTO2("Bbb");
        ModelMapper mapper = new ModelMapper();
        mapper.map(orderDto2, order2);
        assertSame(order2.address, orderDto2.address);
        assertLinesMatch(order2.items, orderDto2.items);
        assertNotEquals(order2.exclude, orderDto2.exclude);
    }
}