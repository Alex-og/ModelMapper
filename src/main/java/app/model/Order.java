package app.model;

import java.util.List;

public class Order {
    @InstanceField("address")
    private String address;
    @InstanceField("city")
    private String city;
    @InstanceField("items")
    private List<String> items;

    @Exclude
    private String exclude;

    public Order() {
    }

    public Order(String address, String city) {
        this.address = address;
        this.city = city;
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

    @InstanceField("city")
    public String getCity() {
        return city;
    }

    @InstanceField("city")
    public void setCity(String city) {
        this.city = city;
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
}
