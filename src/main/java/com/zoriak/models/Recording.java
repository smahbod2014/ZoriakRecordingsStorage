package com.zoriak.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Recording {

    @JsonProperty("recordingId")
    private UUID recordingId;

    public Recording() {}

    public UUID getId() {
        return recordingId;
    }
}
