# Zoriak Recordings Storage

This project allows us to store all our recordings. Nothing more, nothing less.
They are stored directly on the Digital Ocean server in a local directory. It's not
too great since we only have around 15~20GB to work with, but it should be more than
enough for now. Ideally we should be using Amazon S3, but I'm cheap and don't want to
spend any money right now.

## Usage

For testing, Postman is ideal.

### POST

Upload a new recording to the server:

`POST to localhost:9050/recordings`

Make sure you add `form-data` as the `Body` type. Add a new key called `file` and choose any `wav` file

### GET

Retrieve a recording from the server. Best to just input the URL in a browser instead of Postman

`GET to localhost:9050/recordings/{id}`

`id` is in the form of a UUID (e.g. 67199d03-1cae-420d-b932-78617c466861)

## Building the Application

Requirements: Java 8, Maven

Then run:

`mvn clean install`

## Running the Application

`java -jar target/recordings-storage-1.0-SNAPSHOT.jar server zoriak.yml`