import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!\n5Introduce a number:");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number > 0)
            System.out.println("Positive");
        else
            System.out.println("Negative");
    }
}
