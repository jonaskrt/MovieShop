package ch.bzz.bookshelf.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * service for testing
 * <p>
 * Bookshelf
 *
 * @author Marcel Suter (Ghwalin)
 */
@Path("test")
public class TestService {

    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("backupJSON")));

            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("bookJSON"));
            fileOutputStream.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}