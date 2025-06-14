package com.example.go.Class;

/**
 * Represents a text message with an associated email user.
 * This class provides functionality to store and retrieve the content of a message and the email address of the user.
 */
public class text {
    /**
     * The email address of the user associated with the message.
     */
     private String emailUser;
    /**
     * The text content of the message.
     */
    private String messageText;

    private boolean isRead;

    /**
     * Default constructor for the Text class.
     * Initializes a new Text object with no initial content or user.
     */
    public text() {
    }

    /**
     * Constructor for the Text class.
     * Initializes a new Text object with the specified email user and message text.
     *
     * @param emailUser   The email address of the user.
     * @param messageText The text content of the message.
     */
    public text(String emailUser, String messageText) {
        this.messageText = messageText;
        this.emailUser = emailUser;
        this.isRead = false;
    }

    /**
     * Gets the text content of the message.
     *
     * @return The text content of the message.
     */
    public String getContent() {
        return this.messageText;
    }

    /**
     * Sets the text content of the message.
     *
     * @param content The new text content for the message.
     */
    public void setContent(String content) {
        this.messageText = content;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getemailuser() {
        return this.emailUser;
    }

    /**
     * Sets the email address of the user.
     *
     * @param content The new email address for the user.
     */
    public void setemailuser(String content) {
        this.emailUser = content;
    }

    public boolean getIsRead()
    {return this.isRead;}

    public void setIsReadtrue(){
        this.isRead = true;}
}