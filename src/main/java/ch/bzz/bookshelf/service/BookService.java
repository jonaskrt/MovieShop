package ch.bzz.bookshelf.service;

import ch.bzz.bookshelf.data.DataHandler;
import ch.bzz.bookshelf.model.Book;
import ch.bzz.bookshelf.model.Publisher;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the bookshelf
 * <p>
 * M133: Bookshelf
 *
 * @author Marcel Suter
 */
@Path("book")
public class BookService {

    /**
     * produces a map of all books
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listBooks(
    ) {
        Map<String, Book> bookMap = DataHandler.getBookMap();
        Response response = Response
                .status(200)
                .entity(bookMap)
                .build();
        return response;

    }

    /**
     * reads a single book identified by the bookId
     *
     * @param bookUUID the bookUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readBook(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String bookUUID
    ) {
        Book book = null;
        int httpStatus;

        book = DataHandler.readBook(bookUUID);
        if (book.getTitle() != null) {
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }


        Response response = Response
                .status(httpStatus)
                .entity(book)
                .build();
        return response;
    }

    /**
     * creates a new book
     *
     * @param book          a valid book
     * @param publisherUUID the uuid of the publisher
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createBook(
            @Valid @BeanParam Book book,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("publisherUUID") String publisherUUID
    ) {
        int httpStatus;

        book.setBookUUID(UUID.randomUUID().toString());
        Publisher publisher = DataHandler.readPublisher(publisherUUID);
        if (publisher != null) {
            book.setPublisher(publisher);
            DataHandler.insertBook(book);
            httpStatus = 200;
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates an existing book
     *
     * @param book          a valid book
     * @param publisherUUID the uuid of the publisher
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @Valid @BeanParam Book book,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("publisherUUID") String publisherUUID
    ) {
        int httpStatus = 200;


        Book oldBook = DataHandler.readBook(book.getBookUUID());
        if (oldBook.getTitle() != null) {
            httpStatus = 200;
            oldBook.setTitle(book.getTitle());
            oldBook.setAuthor(book.getAuthor());
            oldBook.setPrice(book.getPrice());
            oldBook.setIsbn(book.getIsbn());

            Publisher publisher = DataHandler.readPublisher(publisherUUID);
            oldBook.setPublisher(publisher);
            DataHandler.updateBook();
        } else {
            httpStatus = 404;
        }


        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing book identified by its uuid
     *
     * @param bookUUID the unique key for the book
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String bookUUID
    ) {
        int httpStatus;

        if (DataHandler.deleteBook(bookUUID)) {
            httpStatus = 200;

        } else {
            httpStatus = 404;
        }


        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * sets the attribute values of the book object
     *
     * @param book          the book object
     * @param title         the book title
     * @param author        the author
     * @param publisherUUID the unique key of the publisher
     * @param price         the price
     * @param isbn          the isbn-13 number
     */
    private void setValues(
            Book book,
            String title,
            String author,
            String publisherUUID,
            BigDecimal price,
            String isbn) {
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);
        book.setIsbn(isbn);

        Publisher publisher = DataHandler.getPublisherMap().get(publisherUUID);
        book.setPublisher(publisher);
    }

}
