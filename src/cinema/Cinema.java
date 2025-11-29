package cinema;

import java.util.Scanner;

public class Cinema {

    // ----- Ticket pricing constants -----
    private static final int SMALL_ROOM_LIMIT = 60;  // Border between small and large hall
    private static final int PRICE_STANDARD = 10;    // Standard ticket price
    private static final int PRICE_REDUCED = 8;      // Reduced ticket price for back rows

    /**
     * Reads a positive integer from input.
     * Used for reading the number of rows and seats per row.
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
     * Creates a 2D array representing the cinema hall.
     * All seats are initially marked as free ('S').
     */
    public static char[][] createHall(int rowsCount, int seatsPerRow) {
        char[][] hall = new char[rowsCount][seatsPerRow];

        for (int row = 0; row < rowsCount; row++) {
            for (int seat = 0; seat < seatsPerRow; seat++) {
                hall[row][seat] = 'S';
            }
        }
        return hall;
    }

    /**
     * Displays the current seating layout.
     * Shows row/seat numbers and the S/B seat status.
     */
    public static void showSeats(char[][] hall) {
        int rowsCount = hall.length;
        int seatsPerRow = hall[0].length;

        System.out.println("Cinema:");

        // Print seat numbers
        System.out.print("  ");
        for (int seat = 1; seat <= seatsPerRow; seat++) {
            System.out.print(seat + " ");
        }
        System.out.println();

        // Print rows with seat states
        for (int row = 1; row <= rowsCount; row++) {
            System.out.print(row + " ");
            for (int seat = 1; seat <= seatsPerRow; seat++) {
                System.out.print(hall[row - 1][seat - 1] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Reads an integer in the specified range (inclusive).
     * If message is empty, no prompt is printed.
     */
    public static int readIntInRange(Scanner scanner, String message, int min, int max) {
        int input;

        while (true) {
            try {
                if (!message.isEmpty()) System.out.println(message);
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Error: Number must be between " + min + " and " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number!");
            }
        }
    }

    /**
     * Calculates the price of a ticket based on row and hall size.
     * - Small hall: all seats cost the same.
     * - Large hall: front half is more expensive.
     */
    public static int getTicketPrice(int row, char[][] hall) {
        int rowsCount = hall.length;
        int seatsPerRow = hall[0].length;
        int totalSeats = rowsCount * seatsPerRow;

        int frontRows = rowsCount / 2;

        if (totalSeats <= SMALL_ROOM_LIMIT) return PRICE_STANDARD;
        if (row <= frontRows) return PRICE_STANDARD;
        return PRICE_REDUCED;
    }

    /**
     * Marks the selected seat as booked ('B').
     */
    public static void buyTicket(int row, int seat, char[][] hall) {
        hall[row - 1][seat - 1] = 'B';
    }

    /**
     * Prints the main program menu.
     */
    public static void printMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("0. Exit");
    }

    /**
     * Main menu loop.
     * Continues until the user selects Exit.
     */
    public static void runMenu(Scanner scanner, char[][] hall) {
        while (true) {
            printMenu();
            int input = readIntInRange(scanner, "", 0, 2);

            switch (input) {
                case 1:
                    showSeats(hall);
                    break;

                case 2:
                    int row = readIntInRange(scanner,
                            "Enter a row number:", 1, hall.length);

                    int seat = readIntInRange(scanner,
                            "Enter a seat number in that row:", 1, hall[0].length);

                    System.out.println("Ticket price: $" + getTicketPrice(row, hall));
                    System.out.println();

                    buyTicket(row, seat, hall);
                    break;

                case 0:
                    return; // Exit program
            }
        }
    }

    /**
     * Entry point of the program:
     * - Reads hall dimensions
     * - Builds the hall
     * - Starts the menu loop
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rowsCount = readPositiveInt(scanner, "Enter the number of rows:");
        int seatsPerRow = readPositiveInt(scanner, "Enter the number of seats in each row:");

        char[][] hall = createHall(rowsCount, seatsPerRow);

        runMenu(scanner, hall);

        scanner.close();
    }
}
