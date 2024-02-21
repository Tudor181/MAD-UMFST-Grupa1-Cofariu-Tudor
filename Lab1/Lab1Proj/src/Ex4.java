public class Ex4 {
    public static void main(String[] args) {
        System.out.println("Hello world");
        Ex4Birou birou = new Ex4Birou();
        System.out.println("class name:" + birou.getClass().getName() +
                " Am incapsulat obiectul birou si printez urm date din el care s-au initializat in constructor\nl:"
                + birou.getLatime() + "L:" + birou.getLungime());
    }
}
