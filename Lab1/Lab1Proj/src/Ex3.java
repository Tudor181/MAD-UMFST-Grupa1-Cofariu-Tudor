import java.lang.Math;

public class Ex3 {
    public static boolean isPrim(int nr) {
        boolean prim = true;
        for (int i = 2; i <= Math.sqrt(nr); i++)
            if (nr % i == 0)
                prim = false;
        return prim;
    }

    public static void main(String[] args) {
        System.out.println("Hello world");

        for (int i = 3; i < 100; i = i + 2) {
            if (isPrim(i) && isPrim(i + 2)) {
                int i2 = i + 2;
                System.out.println("(" + i + ", " + i2 + ")");
            }
        }
    }
}
