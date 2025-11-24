package cinema;

import java.util.Scanner;

public class Cinema {
    private static final int SMALL_ROOM_LIMIT = 60;
    private static final int PRICE_STANDARD = 10;
    private static final int PRICE_REDUCED = 8;

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

    public static char[][] createHall(int rows, int seats) {
        char[][] cinema = new char[rows][seats];

        for (int row = 0; row < rows; row++) {
            for (int seat = 0; seat < seats; seat++) {
                cinema[row][seat] = 'S';
            }
        }
        return cinema;
    }

    public static void printHall(char[][] cinema) {
        int rows = cinema.length;
        int seats = cinema[0].length;

        System.out.println("Cinema:");

        System.out.print("  ");
        for (int seat = 1; seat <= seats; seat++) {
            System.out.print(seat + " ");
        }
        System.out.println();

        for (int row = 1; row <= rows; row++) {
            System.out.print(row + " ");
            for (int seat = 1; seat <= seats; seat++) {
                System.out.print(cinema[row - 1][seat - 1] + " ");
            }
            System.out.println();
        }
    }

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

    public static int getTicketPrice(int row, char[][] cinema) {
        int rows =  cinema.length;
        int seats = cinema[0].length;
        int totalSeats = rows * seats;

        int frontRows = rows / 2;

        if (totalSeats <= SMALL_ROOM_LIMIT) return PRICE_STANDARD;
        if (row <= frontRows) return PRICE_STANDARD;
        return  PRICE_REDUCED;
    }

    public static void buyTicket(int row, int seat,  char[][] cinema) {
        cinema[row - 1][seat - 1] = 'B';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = readPositiveInt(scanner, "Enter the number of rows:");
        int seats = readPositiveInt(scanner, "Enter the number of seats in each row:");

        char [][] cinema = createHall(rows, seats);
        printHall(cinema);

        int row = readIntInRange(scanner, "Enter a row number:", rows);
        int seat = readIntInRange(scanner, "Enter a seat number in that row:", seats);

        System.out.println("Ticket price: $" + getTicketPrice(row, cinema));
        System.out.println();
        buyTicket(row, seat, cinema);
        printHall(cinema);
        scanner.close();
    }
}