import java.io.*;
import java.util.Scanner;

public class Ass4 {

    static final int MAX = 100;
    static String[] names = new String[MAX];
    static double[] grades = new double[MAX];
    static int count = 0;
    static final String FILE_NAME = "students.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        loadFromFile();

        int choice;
        do {
            System.out.println("\n==== STUDENT RECORD SYSTEM ====");
            System.out.println("1. Add Record");
            System.out.println("2. View Records");
            System.out.println("3. Update Record");
            System.out.println("4. Delete Record");
            System.out.println("5. Search Record");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            choice = getIntInput(sc);

            switch (choice) {
                case 1: addRecord(sc); break;
                case 2: viewRecords(); break;
                case 3: updateRecord(sc); break;
                case 4: deleteRecord(sc); break;
                case 5: searchRecord(sc); break;
                case 6: saveToFile(); System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }

    // ================= LOAD FILE =================
    static void loadFromFile() {
        File file = new File(FILE_NAME);

        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created: " + FILE_NAME);
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null && count < MAX) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    names[count] = data[0];
                    grades[count] = Double.parseDouble(data[1]);
                    count++;
                }
            }

            br.close();
            System.out.println("Records loaded: " + count);

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }

    // ================= SAVE FILE =================
    static void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (int i = 0; i < count; i++) {
                bw.write(names[i] + "," + grades[i]);
                bw.newLine();
            }

            bw.close();
            System.out.println("Records saved to file.");

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    // ================= ADD =================
    static void addRecord(Scanner sc) {
        if (count >= MAX) {
            System.out.println("Array is full.");
            return;
        }

        System.out.print("Enter name: ");
        names[count] = sc.nextLine();

        System.out.print("Enter grade: ");
        grades[count] = getDoubleInput(sc);

        count++;

        saveToFile();
        System.out.println("Record added.");
    }

    // ================= VIEW =================
    static void viewRecords() {
        if (count == 0) {
            System.out.println("No records found.");
            return;
        }

        System.out.println("\n--- Student Records ---");
        for (int i = 0; i < count; i++) {
            System.out.println(i + ": " + names[i] + " - " + grades[i]);
        }
    }

    // ================= UPDATE =================
    static void updateRecord(Scanner sc) {
        viewRecords();
        if (count == 0) return;

        System.out.print("Enter index to update: ");
        int index = getIntInput(sc);

        if (index < 0 || index >= count) {
            System.out.println("Invalid index.");
            return;
        }

        System.out.print("New name: ");
        names[index] = sc.nextLine();

        System.out.print("New grade: ");
        grades[index] = getDoubleInput(sc);

        saveToFile();
        System.out.println("Record updated.");
    }

    // ================= DELETE =================
    static void deleteRecord(Scanner sc) {
        viewRecords();
        if (count == 0) return;

        System.out.print("Enter index to delete: ");
        int index = getIntInput(sc);

        if (index < 0 || index >= count) {
            System.out.println("Invalid index.");
            return;
        }

        for (int i = index; i < count - 1; i++) {
            names[i] = names[i + 1];
            grades[i] = grades[i + 1];
        }

        count--;

        saveToFile();
        System.out.println("Record deleted.");
    }

    // ================= SEARCH =================
    static void searchRecord(Scanner sc) {
        System.out.print("Enter name to search: ");
        String search = sc.nextLine();

        boolean found = false;

        for (int i = 0; i < count; i++) {
            if (names[i].equalsIgnoreCase(search)) {
                System.out.println("Found at index " + i + ": " + names[i] + " - " + grades[i]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Record not found.");
        }
    }

    // ================= INPUT HELPERS =================
    static int getIntInput(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    static double getDoubleInput(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Invalid input. Enter a valid grade: ");
            sc.next();
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }
}