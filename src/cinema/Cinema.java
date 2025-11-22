package cinema;

public class Cinema {

    public static void main(String[] args) {
        int seats = 8;
        int rows = 7;

        System.out.println("Cinema:");

        System.out.print("  ");
        for (int seat = 1; seat <= seats; seat++) {
            System.out.print(seat + " ");
        }
        System.out.println();

        for (int row = 1; row <= rows; row++) {
            System.out.print(row + " ");
            for (int seat = 1; seat <= seats; seat++) {
                System.out.print("S ");
            }
            System.out.println();
        }

    }
}