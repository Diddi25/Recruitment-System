package com.identification_service.payload.response;

/**
 * Response object containing a message.
 * <p>
 * This class is used to send a message as a response in various API endpoints.
 * </p>
 */
public class MessageResponse {
    private String message;

    /**
     * Constructs a new {@link MessageResponse} with the provided message.
     *
     * @param message the message to be included in the response
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Gets the message from the response.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message in the response.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}