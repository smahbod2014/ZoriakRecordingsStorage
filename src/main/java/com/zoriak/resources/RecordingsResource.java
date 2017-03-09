package com.zoriak.resources;

import com.zoriak.models.Recording;
import com.zoriak.models.RecordingResponse;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

@Path("/recordings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class RecordingsResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordingsResource.class);
    private static final String RECORDINGS_DIRECTORY = "recordings/";

    public RecordingsResource() {
        final File recordingsDir = new File(RECORDINGS_DIRECTORY);
        if (!recordingsDir.isDirectory()) {
            if (!recordingsDir.mkdir()) {
                LOGGER.warn("Failed to create directory '{}'", RECORDINGS_DIRECTORY);
            } else {
                LOGGER.info("Successfully created directory '{}'", RECORDINGS_DIRECTORY);
            }
        } else {
            LOGGER.info("Directory '{}' already exists. Continuing", RECORDINGS_DIRECTORY);
        }
    }

    /**
     * Uploads a new recording
     *
     * @param inputStream the recording
     * @param detail information about the file
     * @return a response indicating if the file was successfully uploaded
     * @throws IOException if the file could not be created for any reason
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public RecordingResponse postFile(@FormDataParam("file") final InputStream inputStream,
                                      @FormDataParam("file") final FormDataContentDisposition detail) throws IOException {
        UUID id = UUID.randomUUID();
        LOGGER.info("Storing a new recording with id {}", id);
        writeToFile(inputStream, RECORDINGS_DIRECTORY + id + ".wav");
        return new RecordingResponse(id);
    }

    /**
     * Retrieves a recording by its id
     *
     * @param id the id of the recording to retrieve in the form of a {@link UUID}
     * @return the recording as a downloadable attachment
     * @throws FileNotFoundException if the recording didn't exist/could not be found
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getRecording(@PathParam("id") final UUID id) throws FileNotFoundException {
        final File recording = new File(RECORDINGS_DIRECTORY + id + ".wav");
        if (!recording.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new FileInputStream(recording))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".wav\"")
                .build();
    }

    /**
     * Helper function to write a file, the recording in this case
     *
     * @param uploadedInputStream the file to write
     * @param uploadedFileLocation the location to write it to as a path
     * @throws IOException if the write failed for any reason
     */
    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
        int read;
        final int BUFFER_LENGTH = 1024;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
    }
}
