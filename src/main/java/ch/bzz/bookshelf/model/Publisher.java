package ch.bzz.bookshelf.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * a publisher of a book
 * <p>
 * Bookshelf
 *
 * @author Marcel Suter (Ghwalin)
 */
public class Publisher {
    @FormParam("publisherUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String publisherUUID;

    @FormParam("publisher")
    @NotEmpty
    @Size(min=5, max=50)
    private String publisher;

    /**
     * Default constructor
     */
    public Publisher() {
        setPublisher(null);
    }
    /**
     * Gets the publisherUUID
     *
     * @return value of publisherUUID
     */
    public String getPublisherUUID() {
        return publisherUUID;
    }

    /**
     * Sets the publisherUUID
     *
     * @param publisherUUID the value to set
     */

    public void setPublisherUUID(String publisherUUID) {
        this.publisherUUID = publisherUUID;
    }

    /**
     * Gets the publisher
     *
     * @return value of publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher
     *
     * @param publisher the value to set
     */

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}