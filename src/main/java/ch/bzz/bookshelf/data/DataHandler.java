package ch.bzz.bookshelf.data;

import ch.bzz.bookshelf.model.Book;
import ch.bzz.bookshelf.model.Publisher;
import ch.bzz.bookshelf.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * data handler for reading and writing the csv files
 * <p>
 * M133: Bookshelf
 *
 * @author Marcel Suter
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Book> bookMap;
    private static Map<String, Publisher> publisherMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        bookMap = new HashMap<>();
        publisherMap = new HashMap<>();
        readJSON("bookJSON");
    }

    /**
     * restores the backup data
     */
    public static void restoreData() {
        bookMap = new HashMap<>();
        publisherMap = new HashMap<>();
        readJSON("backupJSON");
    }

    /**
     * reads a single book identified by its uuid
     *
     * @param bookUUID the identifier
     * @return book-object
     */
    public static Book readBook(String bookUUID) {
        Book book = new Book();
        if (getBookMap().containsKey(bookUUID)) {
            book = getBookMap().get(bookUUID);
        }
        return book;
    }

    /**
     * inserts a new book into the bookmap
     *
     * @param book the book to be saved
     */
    public static void insertBook(Book book) {
        getBookMap().put(book.getBookUUID(), book);
        writeJSON();
    }

    /**
     * updates the bookmap
     */
    public static void updateBook() {
        writeJSON();
    }

    /**
     * removes a book from the bookmap
     *
     * @param bookUUID the uuid of the book to be removed
     * @return success
     */
    public static boolean deleteBook(String bookUUID) {
        if (getBookMap().remove(bookUUID) != null) {
            writeJSON();
            return true;
        } else
            return false;
    }

    /**
     * reads a single publisher identified by its uuid
     *
     * @param publisherUUID the identifier
     * @return publisher-object
     */
    public static Publisher readPublisher(String publisherUUID) {
        Publisher publisher = new Publisher();
        if (getPublisherMap().containsKey(publisherUUID)) {
            publisher = getPublisherMap().get(publisherUUID);
        }
        return publisher;
    }

    /**
     * inserts a new publisher in an empty book
     * @param publisher
     */
    public static void insertPublisher(Publisher publisher) {
        Book book = new Book();
        book.setBookUUID(UUID.randomUUID().toString());
        book.setTitle("");
        book.setPublisher(publisher);
        insertBook(book);
    }

    public static boolean updatePublisher(Publisher publisher) {
        boolean found = false;
        String publisherUUID = publisher.getPublisherUUID();
        if (getPublisherMap().containsKey(publisherUUID)) {
            getPublisherMap().put(publisherUUID, publisher);
            found = true;
        }

        for (Map.Entry<String, Book> entry : getBookMap().entrySet()) {
            Book book = entry.getValue();
            if (book.getPublisher().getPublisherUUID().equals(publisher.getPublisherUUID())) {
                book.setPublisher(publisher);
            }
        }
        writeJSON();
        return found;
    }

    /**
     * deletes a publisher, if it has no books
     * @param publisherUUID
     * @return errorcode  0=ok, -1=referential integrity, 1=not found
     */
    public static int deletePublisher(String publisherUUID) {
        int errorcode = 1;
        for (Map.Entry<String, Book> entry : getBookMap().entrySet()) {
            Book book = entry.getValue();
            if (book.getPublisher().getPublisherUUID().equals(publisherUUID)) {
                if (book.getTitle() == null || book.getTitle().equals("")) {
                    deleteBook(book.getBookUUID());
                } else {
                    return -1;
                }
            }
        }

        if (getPublisherMap().containsKey(publisherUUID)) {
            getPublisherMap().remove(publisherUUID);
            errorcode = 0;
            writeJSON();
        }

        return errorcode;
    }

    /**
     * gets the bookMap
     *
     * @return the bookMap
     */
    public static Map<String, Book> getBookMap() {
        return bookMap;
    }

    /**
     * gets the publisherMap
     *
     * @return the publisherMap
     */
    public static Map<String, Publisher> getPublisherMap() {
        return publisherMap;
    }

    public static void setPublisherMap(Map<String, Publisher> publisherMap) {
        DataHandler.publisherMap = publisherMap;
    }

    /**
     * reads the books and publishers
     */
    private static void readJSON(String propertyName) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty(propertyName)));
            ObjectMapper objectMapper = new ObjectMapper();
            Book[] books = objectMapper.readValue(jsonData, Book[].class);
            for (Book book : books) {
                String publisherUUID = book.getPublisher().getPublisherUUID();
                Publisher publisher;
                if (getPublisherMap().containsKey(publisherUUID)) {
                    publisher = getPublisherMap().get(publisherUUID);
                } else {
                    publisher = book.getPublisher();
                    getPublisherMap().put(publisherUUID, publisher);
                }
                book.setPublisher(publisher);
                getBookMap().put(book.getBookUUID(), book);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the books and publishers
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("bookJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getBookMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
