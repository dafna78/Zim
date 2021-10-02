package utilities;

import javax.annotation.Nullable;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class JavaXMail
{
    private String host;
    private String user;
    private String password;

    public JavaXMail(String _host, String _user, String _password)
    {
        host = _host;
        user = _user;
        password = _password;
    }

    private Session getSession()
    {
        Properties props = new Properties();

        String port = "993";

        // server setting
        props.put("mail.imap.host", host);
        props.put("mail.imap.port", port);

        // SSL setting
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.socketFactory.port", port);

        return Session.getDefaultInstance(props);
    }

    /**
     * Clone messages to enable closing the folder and store
     * @param messages array of email messages to clone
     * @return an array of cloned messages objects
     * @throws MessagingException when failing to create a new message object
     */
    private Message[] cloneMessages(Message[] messages) throws MessagingException
    {
        Message[] clonedMessages = new Message[messages.length];

        for (int i=0; i<messages.length; i++)
        {
            Message copyOfMessage = new MimeMessage((MimeMessage)messages[i]);
            clonedMessages[i] = copyOfMessage;
        }
        return clonedMessages;
    }

    /**
     * Get all matching emails by optional search criteria subject and sender
     * @param folderName name of folder to search in. i.e Inbox
     * @param subject subject of emails to search
     * @param from sender of emails to search
     * @return returns an array of emails that match the search criteria
     * @throws Exception throws NoSuchProviderException, MessagingException
     */
    public Message[] getEmails(String folderName, @Nullable String subject, @Nullable String from) throws Exception
    {
        Session session = getSession();

        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(user, password);

            // opens the folder
            Folder folder = store.getFolder(folderName);
            folder.open(Folder.READ_ONLY);

            // creates a search condition
            SearchTerm searchCondition = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try
                    {
                        //Check if Subject matches
                        if(subject != null || !subject.isEmpty())
                        {
                            if(!message.getSubject().toLowerCase().contains(subject.toLowerCase()))
                                return false;
                        }

                        //Check if From matches
                        if(from != null || !from.isEmpty())
                        {
                            if(Arrays.stream(message.getFrom()).filter(x -> x.toString().toLowerCase().contains(from.toLowerCase())).count() == 0)
                                return false;
                        }

                    }
                    catch (MessagingException ex)
                    {
                        ex.printStackTrace();
                    }
                    return true;
                }
            };

            // performs search through the folder
            Message[] foundMessages = folder.search(searchCondition);

            //clone the message objects to enable closing the folder and store
            Message[] clonedMessages = cloneMessages(foundMessages);

            // disconnect
            folder.close(false);
            store.close();

            return clonedMessages;

        }
        catch (NoSuchProviderException ex)
        {
            throw new Exception("Cannot read emails. No provider." + ex.getMessage());
        }
        catch (MessagingException ex)
        {
            throw new Exception("Could not connect to the message store." + ex.getMessage());
        }
    }

    /**
     * Get a matching email by optional search criteria subject and sender
     * @param folderName name of folder to search in. i.e Inbox
     * @param messageIndex index of email to return in case there are a few emails that match the search criteria
     * @param subject subject of email to search
     * @param from sender of email to search
     * @return returns an email that matches the search criteria or null, if no matches were found
     * @throws Exception throws exceptions for invalid messageIndex values
     */
    public Message getEmail(String folderName, int messageIndex, @Nullable String subject, @Nullable String from) throws Exception
    {
        if(messageIndex < 0)
            throw new Exception(String.format("Invalid value %d. MessageIndex cannot be lower than 0", messageIndex));

        Message[] matchingEmails = getEmails(folderName, subject, from);

        if(matchingEmails.length == 0)
            return null;

        if(messageIndex >= matchingEmails.length)
            throw new Exception(String.format("Invalid value %d. MessageIndex cannot be equal or higher than the number of messages", messageIndex));

        return matchingEmails[messageIndex];
    }

    /**
     * Print the email message to the console
     * @param message the message object
     * @throws javax.mail.MessagingException, if cannot read the content
     * @throws IOException if cannot read the content
     */
    public static void printEmailContent(Message message) throws IOException, MessagingException
    {
        System.out.println("---------------------------------");
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + message.getFrom()[0]);

        printMessagePart(message);
    }

    /**
     * Print each part of the email message to the console
     * @param msgPart the message part
     * @throws javax.mail.MessagingException, if cannot read the content
     * @throws IOException if cannot read the content
     */
    private static void printMessagePart(Part msgPart) throws javax.mail.MessagingException, IOException
    {
        if (msgPart.isMimeType("text/plain"))
        {
            System.out.println("---------------------------");
            System.out.println((String) msgPart.getContent());
        }
        //check if the content has attachment
        else if (msgPart.isMimeType("multipart/*"))
        {
            System.out.println("---------------------------");
            Multipart mp = (Multipart) msgPart.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                printMessagePart(mp.getBodyPart(i));
        }
    }
}
