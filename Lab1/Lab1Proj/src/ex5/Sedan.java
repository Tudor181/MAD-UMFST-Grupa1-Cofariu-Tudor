package ex5;

public class Sedan extends Car {
    private int length;

    public Sedan(int speed, double regularPrice, String color, int length) {
        super(speed, regularPrice, color);
        this.length = length;
    }

    double getSalePrice() {
        double initialPrice = super.getSalePrice();
        if (length > 20) {
            return initialPrice - (initialPrice * 0.05);
        } else {
            return initialPrice * 0.9;
        }
    }

}