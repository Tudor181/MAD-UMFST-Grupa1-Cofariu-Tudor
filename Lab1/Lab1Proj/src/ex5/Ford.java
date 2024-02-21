package ex5;

public class Ford extends Car {
    private int year;
    private int manufacturerDiscount; // percent

    public Ford(int speed, double regularPrice, String color, int year, int manufacturerDiscount) {
        super(speed, regularPrice, color);
        this.year = year;
        this.manufacturerDiscount = manufacturerDiscount;
    }

    @Override
    double getSalePrice() {

        double price = super.getSalePrice() - (super.getSalePrice() * (manufacturerDiscount / 100d));

        return price;
    }
}