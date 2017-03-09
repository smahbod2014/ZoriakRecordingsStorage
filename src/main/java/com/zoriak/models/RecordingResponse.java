package com.zoriak.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class RecordingResponse {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @JsonProperty("recordingId")
    private final UUID recordingId;

    @JsonProperty("dateCreated")
    private final String dateCreated;

    public RecordingResponse(final UUID recordingId) {
        this.recordingId = recordingId;
        this.dateCreated = ZonedDateTime.now(ZoneOffset.UTC).format(formatter);
    }
}
