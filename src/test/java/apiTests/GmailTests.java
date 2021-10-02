package apiTests;

import extentions.ApiActions;
import extentions.Verify;
import helpers.Parse;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.CommonOps;

import javax.mail.Message;
import java.util.HashMap;

@Listeners(utilities.Listeners.class)
public class GmailTests extends CommonOps
{
    String className = this.getClass().getSimpleName();

    @Test(description = "Test01 - Verify Email Exists By Search Criteria")
    @Description("This test searches an email by a specific subject and sender and verifies it exists")
    public void test01_verifyEmailExistsBySearchCriteria() throws Exception
    {
        String testName = "test01";

        HashMap<String, String> testProp = testProperties.getTestProperties(className, testName);

        String folderName = testProp.get("folderName.text");
        String subject = testProp.get("subject.text");
        String from = testProp.get("from.text");
        int emailIndex = Parse.toInt(testProp.get("returnEmail.index"));

        //Get a specific email
        Message message = ApiActions.searchEmail(folderName, emailIndex, subject, from);

        Verify.exists("Verify an email was found with the search criteria", message, String.format("No matching email was found with 'Subject' containing '%s' and/or 'From' containing '%s'.", subject, from));

        //If email was found and assertion passed, print the email content to the console
        ApiActions.printEmail(message);
    }
}
