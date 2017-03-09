package com.zoriak.resources;

import com.zoriak.models.Recording;
import com.zoriak.models.RecordingResponse;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
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

    public RecordingsResource() {

    }

    @Path("/test")
    @POST
    public RecordingResponse postRecording(final Recording recording) {
        LOGGER.info("Received a POST request for recording with id {}", recording.getId());
        return new RecordingResponse(recording.getId());
    }

//    @POST
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public void postFile(@FormDataParam("file") final InputStream inputStream,
//                         @FormDataParam("file") final FormDataContentDisposition detail) throws IOException {
//        LOGGER.info("Posting a file");
//
//        writeToFile(inputStream, "./output.txt");
//
//        Response.ok();
//    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void postFile(@FormDataParam("file") final InputStream inputStream,
                         @FormDataParam("file") final FormDataContentDisposition detail) throws IOException {
        LOGGER.info("Posting a file");

        writeToFile(inputStream, "./output.wav");

        Response.ok();
    }

    // save uploaded file to new location
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
