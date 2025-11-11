package k23cnt3.vtdLesson05.P2.entity;

public class Item {
    private String maItem;
    private String nameItem;
    private double price;

    public Item() {
    }

    public Item(String maItem, String nameItem, double price) {
        this.maItem = maItem;
        this.nameItem = nameItem;
        this.price = price;
    }

    public String getMaItem() {
        return maItem;
    }

    public void setMaItem(String maItem) {
        this.maItem = maItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
