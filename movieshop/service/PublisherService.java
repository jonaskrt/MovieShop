package ch.bzz.movieshop.service;

import ch.bzz.movieshop.data.DataHandler;
import ch.bzz.movieshop.model.Publisher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the publisher
 * <p>
 * M133: Bookshelf
 *
 * @author arcel Suter
 */
@Path("publisher")
public class PublisherService {

    /**
     * produces a map of all publishers
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPublishers(
    ) {
        Map<String, Publisher> publisherMap = DataHandler.getPublisherMap();
        Response response = Response
                .status(200)
                .entity(publisherMap)
                .build();
        return response;
    }

    /**
     * reads a single publisher identified by the uuid
     *
     * @param publisherUUID the UUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPublisher(
            @QueryParam("uuid") String publisherUUID
    ) {
        Publisher publisher = null;
        int httpStatus;

        try {
            UUID.fromString(publisherUUID);
            publisher = DataHandler.readPublisher(publisherUUID);
            if (publisher.getPublisher() != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(publisher)
                .build();
        return response;
    }
}
