package ex5;

public class MyOwnAutoShop {
    public static void main(String[] args) {
        Sedan sedan = new Sedan(200, 20000.93, "red", 5);
        Ford ford1 = new Ford(180, 19123, "mov", 2010, 5);
        Ford ford2 = new Ford(260, 89010.52, "green", 2023, 10);
        Car car = new Car(190, 7890.2, "gray");
        System.out.println("Sedan price: " + sedan.getSalePrice());
        System.out.println("Ford1 price: " + ford1.getSalePrice());
        System.out.println("Ford2 price: " + ford2.getSalePrice());
        System.out.println("Car price: " + car.getSalePrice());
    }
}