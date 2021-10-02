package extentions;

import io.qameta.allure.Step;
import utilities.CommonOps;

import javax.annotation.Nullable;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public class ApiActions extends CommonOps
{
    /**
     * Search all matching emails by optional search criteria subject and sender
     * @param folderName name of folder to search in. i.e Inbox
     * @param subject subject of emails to search
     * @param from sender of emails to search
     * @return returns an array of emails that match the search criteria
     * @throws Exception throws NoSuchProviderException, MessagingException
     */
    @Step("Search all matching emails")
    public static Message[] searchEmails(String folderName, @Nullable String subject, @Nullable String from) throws Exception
    {
        return javaXMail.getEmails(folderName, subject, from);
    }

    /**
     * Search a matching email by optional search criteria subject and sender
     * @param folderName name of folder to search in. i.e Inbox
     * @param messageIndex index of email to return in case there are a few emails that match the search criteria
     * @param subject subject of email to search
     * @param from sender of email to search
     * @return returns an email that matches the search criteria or null, if no matches were found
     * @throws Exception throws exceptions for invalid messageIndex values
     */
    @Step("Search a matching email")
    public static Message searchEmail(String folderName, int messageIndex, @Nullable String subject, @Nullable String from) throws Exception
    {
        return javaXMail.getEmail(folderName, messageIndex, subject, from);
    }

    /**
     * Print the email message to the console
     * @param message the message object
     * @throws javax.mail.MessagingException, if cannot read the content
     * @throws IOException if cannot read the content
     */
    @Step("Print email content")
    public static void printEmail(Message message) throws IOException, MessagingException
    {
        javaXMail.printEmailContent(message);
    }
}
