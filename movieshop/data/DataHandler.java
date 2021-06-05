package ch.bzz.movieshop.data;

import ch.bzz.movieshop.model.Film;
import ch.bzz.movieshop.model.Publisher;
import ch.bzz.movieshop.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * data handler for reading and writing the csv files
 * <p>
 * M133: MovieShop
 *
 * @author Marcel Suter
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Film> filmMap;
    private static Map<String, Publisher> publisherMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        filmMap = new HashMap<>();
        publisherMap = new HashMap<>();
        readJSON();
    }

    /**
     * reads a single film identified by its uuid
     * @param filmUUID  the identifier
     * @return film-object
     */
    public static Film readFilm(String filmUUID) {
        Film film = new Film();
        if (getFilmMap().containsKey(filmUUID)) {
            film = getFilmMap().get(filmUUID);
        }
        return film;
    }

    /**
     * saves a film
     * @param film  the film to be saved
     */
    public static void saveFilm(Film film) {
        getFilmMap().put(film.getFilmUUID(), film);
        writeJSON();
    }

    /**
     * reads a single publisher identified by its uuid
     * @param publisherUUID  the identifier
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
     * saves a publisher
     * @param publisher  the publisher to be saved
     */
    public static void savePublisher(Publisher publisher) {
        getPublisherMap().put(publisher.getPublisherUUID(), publisher);
        writeJSON();
    }

    /**
     * gets the filmMap
     * @return the filmMap
     */
    public static Map<String, Film> getFilmMap() {
        return filmMap;
    }

    /**
     * gets the publisherMap
     * @return the publisherMap
     */
    public static Map<String, Publisher> getPublisherMap() {
        return publisherMap;
    }

    public static void setPublisherMap(Map<String, Publisher> publisherMap) {
        DataHandler.publisherMap = publisherMap;
    }

    /**
     * reads the films and publishers
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("filmJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Film[] films = objectMapper.readValue(jsonData, Film[].class);
            for (Film film : films) {
                String publisherUUID = film.getPublisher().getPublisherUUID();
                Publisher publisher;
                if (getPublisherMap().containsKey(publisherUUID)) {
                    publisher = getPublisherMap().get(publisherUUID);
                } else {
                    publisher = film.getPublisher();
                    getPublisherMap().put(publisherUUID, publisher);
                }
                film.setPublisher(publisher);
                getFilmMap().put(film.getFilmUUID(), film);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the film and publishers
     *
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String filmPath = Config.getProperty("filmJSON");
        try {
            fileOutputStream = new FileOutputStream(filmPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getFilmMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
