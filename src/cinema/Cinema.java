package cinema;

import java.util.Scanner;

public class Cinema {
    private static final int SMALL_ROOM_LIMIT = 60;
    private static final int PRICE_STANDARD = 10;
    private static final int PRICE_REDUCED = 8;

    public static int calculateProfit(int rows, int seatsPerRow) {
        int totalSeats = rows * seatsPerRow;

        if (totalSeats <= SMALL_ROOM_LIMIT) {
            return totalSeats * PRICE_STANDARD;
        }

        int frontRows = rows / 2;
        int backRows = rows - frontRows;

        return (frontRows * seatsPerRow * PRICE_STANDARD) +
                (backRows * seatsPerRow * PRICE_REDUCED);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = 0;
        int seats = 0;

        while (true) {
            try {
                System.out.println("Enter the number of rows:");
                rows = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter the number of seats in each row:");
                seats = Integer.parseInt(scanner.nextLine());

                if (rows > 0 && seats > 0) {
                    break;
                }
                System.out.println("Error: Input must be positive!");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number!");
            }
        }

        System.out.println("Total income: $" + calculateProfit(rows, seats));
        scanner.close();
    }
}