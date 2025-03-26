import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

class Book {
    String isbn;
    String title;
    String author;
    int yearOfPublication;
    String publisher;

    public Book(String isbn, String title, String author, int yearOfPublication, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + ", Title: " + title + ", Author: " + author +
                ", Year: " + yearOfPublication + ", Publisher: " + publisher;
    }
}

public class BookManagement {
    private LinkedList<Book> bookList;

    public BookManagement() {
        bookList = new LinkedList<>();
    }

    private String[] parseCSVLine(String line) {
        LinkedList<String> values = new LinkedList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(field.toString().trim());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        values.add(field.toString().trim());
        return values.toArray(new String[0]);
    }

    public void readAndInsertFromCSV(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] values = parseCSVLine(line);
                if (values.length >= 5) {
                    try {
                        String isbn = values[0].trim();
                        String title = values[1].trim();
                        String author = values[2].trim();
                        int year = Integer.parseInt(values[3].trim());
                        String publisher = values[4].trim();

                        Book book = new Book(isbn, title, author, year, publisher);
                        insert(book);
                    } catch (NumberFormatException e) {
                        System.err.println(e);
                    }
                }
            }
        }
    }

    public void insert(Book book) {
        bookList.add(book);
    }

    public Book searchFirst() {
        return bookList.isEmpty() ? null : bookList.getFirst();
    }

    public void deleteFirst() {
        if (!bookList.isEmpty()) {
            bookList.removeFirst();
        }
    }

    public void displayAll() {
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

    public static void main(String[] args) {
        BookManagement bookMgr = new BookManagement();
        String filePath = "books.csv";

        try {
            long startTime = System.nanoTime();
            bookMgr.readAndInsertFromCSV(filePath);
            long endTime = System.nanoTime();
            long insertionTime = endTime - startTime;

            System.out.println("LinkedList after insertion from CSV:");
            bookMgr.displayAll();
            System.out.println("Time to store data in LinkedList: " + insertionTime + " nanoseconds");

            startTime = System.nanoTime();
            Book firstBook = bookMgr.searchFirst();
            endTime = System.nanoTime();
            long searchTime = endTime - startTime;

            System.out.println("\nFirst element in LinkedList: " + (firstBook != null ? firstBook : "List is empty"));
            System.out.println("Time to search the first element: " + searchTime + " nanoseconds");

            bookMgr.deleteFirst();
            System.out.println("\nLinkedList after deleting the first element:");
            bookMgr.displayAll();

            System.out.println("\nTime Complexity Evaluation:");
            System.out.println("1. Storing Data in LinkedList:");
            System.out.println("   - Average Case: O(n), where n is the number of books.");
            System.out.println("   - Worst Case: O(n), as each insertion at the end takes O(1) but we do it n times.");
            System.out.println("   - For this dataset, it's consistently O(n) regardless of input order.");

            System.out.println("2. Searching the First Element in LinkedList:");
            System.out.println("   - Average Case: O(1), as we can directly access the first element.");
            System.out.println("   - Worst Case: O(1), since LinkedList maintains a reference to the head.");
            System.out.println("   - For this dataset, always O(1).");

            System.out.println("3. Deleting the First Element in LinkedList:");
            System.out.println("   - Average Case: O(1), as removal from the head is constant time.");
            System.out.println("   - Worst Case: O(1), since we only need to update the head reference.");
            System.out.println("   - For this dataset, always O(1).");

            System.out.println("\nWhy I Chose the First Element for Evaluation:");
            System.out.println("- The first element is efficiently accessible in a LinkedList (O(1) operations).");
            System.out.println("- It demonstrates LinkedList's strength in adding/removing from ends.");
            System.out.println("- Unlike BST, LinkedList doesn't require tree balancing considerations.");
            System.out.println("- This choice highlights LinkedList's simplicity and predictable performance.");

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}