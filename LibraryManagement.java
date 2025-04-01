import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;
    private LocalDate dueDate;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.dueDate = null;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public LocalDate getDueDate() { return dueDate; }

    public void borrowBook() {
        isAvailable = false;
        dueDate = LocalDate.now().plusDays(7); // Due in 7 days
    }

    public long returnBook() {
        isAvailable = true;
        long overdueDays = 0;
        if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
            overdueDays = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        }
        dueDate = null; // Reset due date
        return overdueDays;
    }

    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Borrowed (Due: " + dueDate + ")";
        return id + ". " + title + " by " + author + " [" + status + "]";
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private int bookCounter = 1;
    private static final int FINE_PER_DAY = 5; // Fine amount per overdue day

    public void addBook(String title, String author) {
        books.add(new Book(bookCounter++, title, author));
        System.out.println("Book added successfully!");
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public void borrowBook(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                if (book.isAvailable()) {
                    book.borrowBook();
                    System.out.println("Book borrowed successfully! Due Date: " + book.getDueDate());
                } else {
                    System.out.println("Book is already borrowed.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void returnBook(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                if (!book.isAvailable()) {
                    long overdueDays = book.returnBook();
                    if (overdueDays > 0) {
                        int fine = (int) overdueDays * FINE_PER_DAY;
                        System.out.println("Book returned late! Fine: $" + fine);
                    } else {
                        System.out.println("Book returned on time. No fine.");
                    }
                } else {
                    System.out.println("This book was not borrowed.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Adding some default books
        library.addBook("Harry Potter", "J.K. Rowling");
        library.addBook("The Hobbit", "J.R.R. Tolkien");
        library.addBook("1984", "George Orwell");

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. View Books");
            System.out.println("2. Add New Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    library.viewBooks();
                    break;
                case 2:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;
                case 3:
                    System.out.print("Enter Book ID to borrow: ");
                    int borrowId = scanner.nextInt();
                    library.borrowBook(borrowId);
                    break;
                case 4:
                    System.out.print("Enter Book ID to return: ");
                    int returnId = scanner.nextInt();
                    library.returnBook(returnId);
                    break;
                case 5:
                    System.out.println("Exiting Library Management System...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
