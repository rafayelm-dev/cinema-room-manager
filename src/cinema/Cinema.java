package cinema;

import java.util.Scanner;

public class Cinema {

    // Constants for ticket pricing rules
    private static final int SMALL_ROOM_LIMIT = 60; // Threshold where all seats cost $10
    private static final int PRICE_STANDARD = 10;   // Price for small rooms and front half of large rooms
    private static final int PRICE_REDUCED = 8;     // Price for back half of large rooms

    /**
     * Reads any positive integer from user input.
     * Used for reading cinema dimensions (rows, seats).
     */
    public static int readPositiveInt(Scanner scanner, String message) {
        int input;

        while (true) {
            try {
                System.out.println(message);
                input = Integer.parseInt(scanner.nextLine());
                if (input > 0) {
                    return input;
                }
                System.out.println("Error: Input must be positive!");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number!");
            }
        }
    }

    /**
     * Creates the cinema hall as a 2D array filled with 'S' (available seats).
     */
    public static char[][] createHall(int rows, int seats) {
        char[][] cinema = new char[rows][seats];

        for (int row = 0; row < rows; row++) {
            for (int seat = 0; seat < seats; seat++) {
                cinema[row][seat] = 'S';
            }
        }
        return cinema;
    }

    /**
     * Prints the hall layout with row and seat numbers.
     * Example:
     *   1 2 3 ...
     * 1 S S S ...
     */
    public static void printHall(char[][] cinema) {
        int rows = cinema.length;
        int seats = cinema[0].length;

        System.out.println("Cinema:");

        // Print top seat numbers
        System.out.print("  ");
        for (int seat = 1; seat <= seats; seat++) {
            System.out.print(seat + " ");
        }
        System.out.println();

        // Print each row with row number
        for (int row = 1; row <= rows; row++) {
            System.out.print(row + " ");
            for (int seat = 1; seat <= seats; seat++) {
                System.out.print(cinema[row - 1][seat - 1] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Reads a number that must be within the range [1, max].
     * Used for selecting an existing row/seat inside the hall.
     */
    public static int readIntInRange(Scanner scanner, String message, int max) {
        int input;

        while (true) {
            try {
                System.out.println(message);
                input = Integer.parseInt(scanner.nextLine());
                if (input > 0 && input <= max) {
                    return input;
                }
                System.out.println("Error: Number must be between 1 and " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number!");
            }
        }
    }

    /**
     * Determines the ticket price based on cinema rules:
     * - If total seats <= 60 → all tickets cost $10
     * - Otherwise → rows in the front half cost $10, back half cost $8
     */
    public static int getTicketPrice(int row, char[][] cinema) {
        int rows =  cinema.length;
        int seats = cinema[0].length;
        int totalSeats = rows * seats;

        int frontRows = rows / 2;

        if (totalSeats <= SMALL_ROOM_LIMIT) return PRICE_STANDARD;
        if (row <= frontRows) return PRICE_STANDARD;
        return  PRICE_REDUCED;
    }

    /**
     * Marks a seat as purchased ('B').
     */
    public static void buyTicket(int row, int seat,  char[][] cinema) {
        cinema[row - 1][seat - 1] = 'B';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read hall dimensions
        int rows = readPositiveInt(scanner, "Enter the number of rows:");
        int seats = readPositiveInt(scanner, "Enter the number of seats in each row:");

        // Build and display empty hall
        char [][] cinema = createHall(rows, seats);
        printHall(cinema);

        // Read seat coordinates inside the valid range
        int row = readIntInRange(scanner, "Enter a row number:", rows);
        int seat = readIntInRange(scanner, "Enter a seat number in that row:", seats);

        // Show ticket price for selected seat
        System.out.println("Ticket price: $" + getTicketPrice(row, cinema));
        System.out.println();

        // Buy the ticket and update the hall
        buyTicket(row, seat, cinema);
        printHall(cinema);
        
        scanner.close();
    }
}