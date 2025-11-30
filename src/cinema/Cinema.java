package cinema;

import java.util.Scanner;

public class Cinema {

    // ----- Pricing constants -----
    private static final int SMALL_ROOM_LIMIT = 60;
    private static final int PRICE_STANDARD = 10;
    private static final int PRICE_REDUCED = 8;

    // ----- Runtime statistics -----
    // Number of tickets sold so far
    private static int purchasedTickets = 0;
    // Money earned from sold tickets
    private static int currentIncome = 0;
    // Maximum possible income if all tickets are sold
    private static int totalIncome = 0;

    /**
     * Calculates the total possible income for the whole cinema hall.
     * Uses the rules from the project: small hall – all seats at the same price,
     * large hall – front half more expensive, back half cheaper.
     */
    private static int calculateTotalIncome(int rows, int seatsPerRow) {
        int totalSeats = rows * seatsPerRow;

        if (totalSeats <= SMALL_ROOM_LIMIT) {
            return totalSeats * PRICE_STANDARD;
        }

        int frontRows = rows / 2;
        int backRows = rows - frontRows;

        return (frontRows * seatsPerRow * PRICE_STANDARD) +
                (backRows * seatsPerRow * PRICE_REDUCED);
    }

    /**
     * Calculates the percentage of purchased tickets.
     */
    private static double calculatePercentage(char[][] hall) {
        int rows = hall.length;
        int seatsPerRow = hall[0].length;
        int totalSeats = rows * seatsPerRow;
        return (double) purchasedTickets / totalSeats * 100;
    }

    /**
     * Updates runtime statistics after a successful ticket purchase.
     */
    private static void updateStatistics(int ticketPrice) {
        purchasedTickets++;
        currentIncome += ticketPrice;
    }

    /**
     * Prints the statistics required by the project:
     * number of purchased tickets, percentage, current and total income.
     */
    private static void showStatistics(char[][] hall) {
        System.out.println();
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println("Percentage: " + String.format("%.2f%%", calculatePercentage(hall)));
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    /**
     * Reads a positive integer from the user.
     * Used to read the number of rows and seats in each row.
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
     * Creates the cinema hall layout.
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
     * Prints the current seating layout:
     * seat numbers in the header and rows filled with 'S'/'B'.
     */
    public static void showSeats(char[][] hall) {
        int rowsCount = hall.length;
        int seatsPerRow = hall[0].length;

        System.out.println("Cinema:");

        // Print header with seat numbers
        System.out.print("  ");
        for (int seat = 1; seat <= seatsPerRow; seat++) {
            System.out.print(seat + " ");
        }
        System.out.println();

        // Print each row and its seat states
        for (int row = 1; row <= rowsCount; row++) {
            System.out.print(row + " ");
            for (int seat = 1; seat <= seatsPerRow; seat++) {
                System.out.print(hall[row - 1][seat - 1] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns the ticket price for a given row according to the pricing rules.
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
    public static void buyTicket(int row, int seat,  char[][] hall) {
        hall[row - 1][seat - 1] = 'B';
    }

    /**
     * Prints the main menu of the program.
     */
    public static void showMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    /**
     * Reads a menu option from the user.
     * Returns -1 if the input is not a valid integer.
     */
    public static int readMenuOption(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Checks if the given coordinates are inside the hall boundaries.
     */
    public static boolean isValidCoordinates(int row, int seat, char[][] hall) {
        boolean rowInValidRange = row >= 1 && row <= hall.length;
        boolean seatInValidRange = seat >= 1 && seat <= hall[0].length;
        return rowInValidRange && seatInValidRange;
    }

    /**
     * Checks if the given seat is free (marked as 'S').
     */
    public static boolean isSeatAvailable(int row, int seat, char[][] hall) {
        return hall[row - 1][seat - 1] == 'S';
    }

    /**
     * Reads seat coordinates (row and seat) from the user.
     * Repeats input until the user selects a valid and available seat.
     * Handles:
     * - wrong input format
     * - coordinates out of bounds
     * - already purchased seats
     */
    public static int[] readSeatCoordinates(Scanner scanner, char[][] hall) {
        int[] coordinates = new int[2];

        while (true) {
            System.out.println("Enter a row number:");
            String row = scanner.nextLine();
            System.out.println("Enter a seat number in that row:");
            String seat = scanner.nextLine();

            try {
                coordinates[0] = Integer.parseInt(row);
                coordinates[1] = Integer.parseInt(seat);

                if (!isValidCoordinates(coordinates[0], coordinates[1], hall)) {
                    System.out.println("Wrong input!");
                    continue;
                }

                if (!isSeatAvailable(coordinates[0], coordinates[1], hall)) {
                    System.out.println("That ticket has already been purchased!");
                    continue;
                }

                return coordinates;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input!");
            }
        }
    }

    /**
     * Main menu loop: shows the menu and handles user actions
     * until the user chooses to exit.
     */
    public static void runMenu(Scanner scanner, char[][] hall) {
        while (true) {
            showMenu();
            int choice = readMenuOption(scanner);

            switch (choice) {
                case 1:
                    showSeats(hall);
                    break;

                case 2:
                    int[] coordinates = readSeatCoordinates(scanner, hall);
                    int row = coordinates[0];
                    int seat = coordinates[1];

                    int ticketPrice = getTicketPrice(row, hall);
                    System.out.println("Ticket price: $" + ticketPrice);
                    System.out.println();

                    buyTicket(row, seat, hall);
                    updateStatistics(ticketPrice);
                    break;

                case 3:
                    showStatistics(hall);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Error: Number must be between 0 and 3!");
            }
        }
    }

    /**
     * Entry point of the program:
     * - reads hall dimensions
     * - calculates total income
     * - creates the hall
     * - starts the menu loop
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rowsCount = readPositiveInt(scanner, "Enter the number of rows:");
        int seatsPerRow = readPositiveInt(scanner, "Enter the number of seats in each row:");
        totalIncome = calculateTotalIncome(rowsCount, seatsPerRow);

        char [][] hall = createHall(rowsCount, seatsPerRow);

        runMenu(scanner, hall);

        scanner.close();
    }
}
