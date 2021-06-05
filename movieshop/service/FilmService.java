package ch.bzz.movieshop.service;

import ch.bzz.movieshop.data.DataHandler;
import ch.bzz.movieshop.model.Film;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the bookshelf
 * <p>
 * M133: Bookshelf
 *
 * @author Marcel Suter
 */
@Path("film")
public class FilmService {

    /**
     * produces a map of all films
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFilms(
    ) {
        Map<String, Film> filmMap = DataHandler.getFilmMap();
        Response response = Response
                .status(200)
                .entity(filmMap)
                .build();
        return response;

    }

    /**
     * reads a single film identified by the filmId
     *
     * @param filmUUID the filmUUID in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readFilm(
            @QueryParam("uuid") String filmUUID
    ) {
        Film film = null;
        int httpStatus;

        try {
            UUID filmKey = UUID.fromString(filmUUID);
            film = DataHandler.readFilm(filmUUID);
            if (film.getTitle() != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(film)
                .build();
        return response;
    }
}
