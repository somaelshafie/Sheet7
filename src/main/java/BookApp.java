import java.io.*;
import java.util.ArrayList;

public class BookApp {
    public static void main(String[] args) {

        BookManager b = new BookManager();

        Book b1 = new Book("book1", "sama", 120.0);
        Book b2 = new Book("book2", "sara", 100.0);
        Book b3 = new Book("book3", "Hussein", 90.0);

        b.addBook(b1);
        b.addBook(b2);
        b.addBook(b3);

        b.writeBooksToTextFile("books.txt");

        b.readBooksFromTextFile("books.txt");

        b.saveBooks("books.dat");

        b.loadBooks("books.dat");
    }
}

class Book implements Serializable {

    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String toString() {
        return   title + "," + author + "," + price;
    }
}

class BookManager {

    ArrayList<Book> books = new ArrayList<>();

    void addBook(Book b) {
        books.add(b);
    }

    void writeBooksToTextFile(String fileName) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 0; i < books.size(); i++) {
                writer.write(books.get(i).toString());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void readBooksFromTextFile(String fileName) {

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                Book b = new Book(
                        data[0],
                        data[1],
                        Double.parseDouble(data[2])
                );

                addBook(b);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void saveBooks(String fileName) {

        for (int i = 0; i < books.size(); i++) {
            for (int j = i + 1; j < books.size(); j++) {

                if (books.get(i).getPrice() <
                        books.get(j).getPrice()) {

                    Book temp = books.get(i);
                    books.set(i, books.get(j));
                    books.set(j, temp);
                }
            }
        }

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            out.writeObject(books);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void loadBooks(String fileName) {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            ArrayList<Book> b =
                    (ArrayList<Book>) in.readObject();

            for (int i = 0; i < b.size(); i++) {
                System.out.println(b.get(i));
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}