import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

public class LibraryTest {
    private Library library;
    private Book book;

    @Before
    public void setUp() {
        library = new Library();
        library.addBook("Test Book", "Test Author");
    }

    @Test
    public void testAddBook() {
        int initialSize = library.books.size();
        library.addBook("New Book", "New Author");
        assertEquals(initialSize + 1, library.books.size());
    }

    @Test
    public void testBorrowBook() {
        book = library.books.get(0);
        assertTrue(book.isAvailable());
        book.borrowBook();
        assertFalse(book.isAvailable());
        assertNotNull(book.getDueDate());
    }

    @Test
    public void testReturnBookNoFine() {
        book = library.books.get(0);
        book.borrowBook();
        long fine = book.returnBook();
        assertTrue(book.isAvailable());
        assertEquals(0, fine);
    }

    @Test
    public void testReturnBookWithFine() {
        book = library.books.get(0);
        book.borrowBook();
        book.dueDate = LocalDate.now().minusDays(5); // Overdue by 5 days
        long fine = book.returnBook();
        assertEquals(5, fine);
    }
}
