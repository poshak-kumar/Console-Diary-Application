import java.io.*;
import java.util.Scanner;

public class PersonalDiary {
    private static final String DIARY_PATH = "diary_entries/";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        File dir = new File(DIARY_PATH); 
        if (!dir.exists()) {
            dir.mkdir(); // Create directory if it doesn't exist
        }

        while (true) {
            System.out.println("\nPersonal Diary Application");
            System.out.println("1. Create New Entry");
            System.out.println("2. View Entry");
            System.out.println("3. Edit Entry");
            System.out.println("4. Delete Entry");
            System.out.println("5. List All Entries");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createEntry(scanner);
                case 2 -> viewEntry(scanner);
                case 3 -> editEntry(scanner);
                case 4 -> deleteEntry(scanner);
                case 5 -> listEntries();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void createEntry(Scanner scanner) throws IOException {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        File file = new File(DIARY_PATH + "diary_" + date + ".txt");

        if (file.exists()) {
            System.out.println("Entry already exists for this date.");
            return;
        }

        System.out.println("Write your diary entry: ");
        String content = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            System.out.println("Diary entry saved successfully!");
        }
    }

    private static void viewEntry(Scanner scanner) throws IOException {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        File file = new File(DIARY_PATH + "diary_" + date + ".txt");

        if (!file.exists()) {
            System.out.println("No entry found for this date.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("Diary Entry for " + date + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private static void editEntry(Scanner scanner) throws IOException {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        File file = new File(DIARY_PATH + "diary_" + date + ".txt");

        if (!file.exists()) {
            System.out.println("No entry found for this date.");
            return;
        }

        System.out.println("Current entry:");
        viewEntry(scanner);

        System.out.println("Enter new content to append:");
        String newContent = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.newLine();
            writer.write(newContent);
            System.out.println("Entry updated successfully!");
        }
    }

    private static void deleteEntry(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        File file = new File(DIARY_PATH + "diary_" + date + ".txt");

        if (file.delete()) {
            System.out.println("Diary entry for " + date + " deleted.");
        } else {
            System.out.println("No entry found for this date.");
        }
    }

    private static void listEntries() { // This method show all diary entries
        File dir = new File(DIARY_PATH);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files != null && files.length > 0) {
            System.out.println("All Diary Entries:");
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("No diary entries found.");
        }
    }
}
