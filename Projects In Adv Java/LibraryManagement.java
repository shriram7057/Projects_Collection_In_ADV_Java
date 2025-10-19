// LibraryManagement.java
/*
 * 📚 Library Management System
 * Features:
 * - Add new books
 * - View all books
 * - Issue a book
 * - Return a book
 * - Search books (NEW)
 * - Save and Load data (NEW)
 */

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

// 1. Make Book class Serializable
class Book implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    String id, title, author;
    boolean issued = false;

    Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-30s | %-20s | %s",
            id, title, author, (issued ? "ISSUED 🚫" : "AVAILABLE ✅"));
    }
}

public class LibraryManagement {
    static ArrayList<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    // Define the file name for persistence
    private static final String DATA_FILE = "library_data.ser";

    public static void main(String[] args) {
        // 2. Load data at startup
        loadBooksFromFile();
        System.out.println("Library System Initialized. Loaded " + books.size() + " books.");

        while (true) {
            System.out.println("\n===== Library Menu =====");
            // 3. Add Search option
            System.out.println("1. Add Book\n2. View Books\n3. Issue Book\n4. Return Book\n5. Search Books\n6. Exit and Save");
            System.out.print("Enter choice: ");

            if (sc.hasNextInt()) {
                int ch = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (ch) {
                    case 1 -> addBook();
                    case 2 -> viewBooks();
                    case 3 -> issueBook();
                    case 4 -> returnBook();
                    case 5 -> searchBooks(); // NEW feature
                    case 6 -> {
                        // 4. Save data before exiting
                        saveBooksToFile(); 
                        System.out.println("Goodbye! 👋 Data saved successfully.");
                        sc.close();
                        System.exit(0);
                    }
                    default -> System.out.println("⚠️ Invalid choice! Please enter a number between 1 and 6.");
                }
            } else {
                System.out.println("⚠️ Invalid input! Please enter a number.");
                sc.nextLine(); // Consume the invalid input
            }
        }
    }

    // --- Persistence Methods (NEW) ---

    @SuppressWarnings("unchecked") // Suppress warning for casting Object to ArrayList<Book>
    private static void loadBooksFromFile() {
        try (FileInputStream fileIn = new FileInputStream(DATA_FILE);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            books = (ArrayList<Book>) objectIn.readObject();
        } catch (FileNotFoundException e) {
            // File not found is normal on first run; initialize with test data
            System.out.println("💾 No existing data file found. Initializing with test data.");
            books.add(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald"));
            books.add(new Book("B002", "1984", "George Orwell"));
            books.add(new Book("B003", "Pride and Prejudice", "Jane Austen"));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void saveBooksToFile() {
        try (FileOutputStream fileOut = new FileOutputStream(DATA_FILE);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
             
            objectOut.writeObject(books);
            System.out.println("💾 Data successfully saved to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Search Method (NEW) ---

    static void searchBooks() {
        System.out.print("Enter search term (ID, Title, or Author): ");
        String query = sc.nextLine().trim().toLowerCase();

        if (query.isEmpty()) {
            System.out.println("⚠️ Search query cannot be empty.");
            return;
        }

        // Use Java Streams to filter the list efficiently
        List<Book> results = books.stream()
            .filter(b -> b.id.toLowerCase().contains(query) ||
                         b.title.toLowerCase().contains(query) ||
                         b.author.toLowerCase().contains(query))
            .collect(Collectors.toList());

        System.out.println("\n" + "=".repeat(70));
        System.out.println("SEARCH RESULTS for: '" + query + "' (" + results.size() + " found)");
        System.out.println("=".repeat(70));

        if (results.isEmpty()) {
            System.out.println("No books matched your search term.");
            return;
        }

        System.out.println(String.format("%-5s | %-30s | %-20s | %s", "ID", "TITLE", "AUTHOR", "STATUS"));
        System.out.println("-".repeat(70));
        for (Book b : results) {
            System.out.println(b);
        }
        System.out.println("=".repeat(70));
    }
    
    // --- Existing Methods (Unchanged for brevity) ---

    static void addBook() {
        System.out.print("Enter Book ID (e.g., L004): ");
        String id = sc.nextLine().trim();

        if (books.stream().anyMatch(b -> b.id.equalsIgnoreCase(id))) {
            System.out.println("❌ Error: Book with ID '" + id + "' already exists.");
            return;
        }

        System.out.print("Enter Title (can contain spaces): ");
        String title = sc.nextLine().trim(); 

        System.out.print("Enter Author (can contain spaces): ");
        String author = sc.nextLine().trim(); 
        
        if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            System.out.println("❌ Error: ID, Title, and Author cannot be empty.");
            return;
        }

        books.add(new Book(id, title, author));
        System.out.println("✅ Book '" + title + "' by " + author + " added!");
    }

    static void viewBooks() {
        // ... (viewBooks implementation remains the same)
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALL BOOKS IN LIBRARY");
        System.out.println("=".repeat(70));
        
        if (books.isEmpty()) {
            System.out.println("The library is empty. Add some books!");
            return;
        }
        
        System.out.println(String.format("%-5s | %-30s | %-20s | %s", "ID", "TITLE", "AUTHOR", "STATUS"));
        System.out.println("-".repeat(70));
        
        for (Book b : books) {
            System.out.println(b);
        }
        System.out.println("=".repeat(70));
    }

    private static Book findBook(String id) {
        for (Book b : books) {
            if (b.id.equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }

    static void issueBook() {
        System.out.print("Enter Book ID to issue: ");
        String id = sc.nextLine().trim();
        Book b = findBook(id);

        if (b == null) {
            System.out.println("❌ Book not found with ID: " + id);
        } else if (b.issued) {
            System.out.println("❌ Book '" + b.title + "' is already issued.");
        } else {
            b.issued = true;
            System.out.println("📕 Book '" + b.title + "' issued successfully!");
        }
    }

    static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        String id = sc.nextLine().trim();
        Book b = findBook(id);

        if (b == null) {
            System.out.println("❌ Book not found with ID: " + id);
        } else if (!b.issued) {
            System.out.println("❌ Book '" + b.title + "' was not issued (it is already available).");
        } else {
            b.issued = false;
            System.out.println("📗 Book '" + b.title + "' returned! Thank you.");
        }
    }
}