import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Room {

    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Reservation {

    String guestName;
    int roomNumber;
    String category;
    double amount;
    String paymentStatus;

    Reservation(String guestName, int roomNumber, String category,
                double amount, String paymentStatus) {

        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
}

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    static String fileName = "reservations.txt";

    public static void addRooms() {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4000));
    }

    public static void searchRooms() {

        System.out.print("\nEnter room category (Standard/Deluxe/Suite): ");
        String category = sc.nextLine();

        boolean found = false;

        System.out.println("\nAvailable Rooms:");

        for (Room room : rooms) {

            if (room.category.equalsIgnoreCase(category)
                    && room.available) {

                System.out.println(
                    "Room " + room.roomNumber
                    + " | " + room.category
                    + " | Rs. " + room.price
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available in this category.");
        }
    }

    public static void bookRoom() {

        System.out.print("\nEnter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();

        Room selectedRoom = null;

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!selectedRoom.available) {
            System.out.println("Sorry, this room is already booked.");
            return;
        }

        System.out.println("\nRoom Details");
        System.out.println("Category: " + selectedRoom.category);
        System.out.println("Price: Rs. " + selectedRoom.price);

        System.out.print("Confirm booking? (yes/no): ");
        String confirm = sc.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled.");
            return;
        }

        System.out.print("Enter payment amount: Rs. ");
        double payment = sc.nextDouble();
        sc.nextLine();

        if (payment == selectedRoom.price) {

            selectedRoom.available = false;

            Reservation reservation = new Reservation(
                name,
                selectedRoom.roomNumber,
                selectedRoom.category,
                selectedRoom.price,
                "Paid"
            );

            reservations.add(reservation);

            saveReservation(reservation);

            System.out.println("\nBooking successful!");
            System.out.println("Room Number: " + selectedRoom.roomNumber);
            System.out.println("Guest Name: " + name);
            System.out.println("Payment Status: Paid");

        } else {

            System.out.println(
                "Payment amount does not match room price."
            );
        }
    }

    public static void viewReservations() {

        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations found.");
            return;
        }

        System.out.println("\n========== RESERVATIONS ==========");

        for (Reservation r : reservations) {

            System.out.println("Guest Name: " + r.guestName);
            System.out.println("Room Number: " + r.roomNumber);
            System.out.println("Category: " + r.category);
            System.out.println("Amount: Rs. " + r.amount);
            System.out.println("Payment: " + r.paymentStatus);
            System.out.println("----------------------------------");
        }
    }

    public static void cancelReservation() {

        System.out.print("\nEnter room number to cancel: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();

        Reservation foundReservation = null;

        for (Reservation r : reservations) {

            if (r.roomNumber == roomNumber) {
                foundReservation = r;
                break;
            }
        }

        if (foundReservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        reservations.remove(foundReservation);

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber) {
                room.available = true;
                break;
            }
        }

        saveAllReservations();

        System.out.println("Reservation cancelled successfully.");
    }

    public static void saveReservation(Reservation r) {

        try {

            FileWriter writer = new FileWriter(fileName, true);

            writer.write(
                r.guestName + " | "
                + r.roomNumber + " | "
                + r.category + " | "
                + r.amount + " | "
                + r.paymentStatus + "\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving reservation.");
        }
    }

    public static void saveAllReservations() {

        try {

            FileWriter writer = new FileWriter(fileName);

            for (Reservation r : reservations) {

                writer.write(
                    r.guestName + " | "
                    + r.roomNumber + " | "
                    + r.category + " | "
                    + r.amount + " | "
                    + r.paymentStatus + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error updating reservation file.");
        }
    }

    public static void main(String[] args) {

        addRooms();

        int choice;

        do {

            System.out.println("\n========== HOTEL RESERVATION SYSTEM ==========");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View Reservations");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Exit");
            System.out.println("==============================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    viewReservations();
                    break;

                case 4:
                    cancelReservation();
                    break;

                case 5:
                    System.out.println(
                        "\nThank you for using Hotel Reservation System!"
                    );
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Please try again."
                    );
            }

        } while (choice != 5);

        sc.close();
    }
}