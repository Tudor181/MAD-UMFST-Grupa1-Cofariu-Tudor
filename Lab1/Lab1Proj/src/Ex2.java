import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        System.out.print("introduce n: ");
        Scanner sc = new Scanner(System.in);

        int nr = sc.nextInt();
        int vector2[] = new int[nr];
        for (int i = 0; i < nr; i++) {
            int v = sc.nextInt();
            vector2[i] = v;

        }
        double suma = 0;
        for (int x : vector2) {
            suma += x;
        }
        suma /= vector2.length;
        System.out.println("The average value is: " + suma);
    }
}