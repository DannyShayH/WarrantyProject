package app.services.notificationServices;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class EmailService {

    private final String apiKey;

    public EmailService(String apiKey){
        this.apiKey = apiKey;
    }

    public void sendWarrantyReminder(String toEmail, String productName, long daysLeft){
        try{
            Email from = new Email("tourwarranty@gmail.com");
            Email to = new Email(toEmail);
            String subject = "Warranty Reminder";

            String contextText = "Your Warranty For " + productName + " Expires in " + daysLeft + " Days.";

            Content content = new Content("text/plain", contextText);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            sg.api(request);

            Response response = sg.api(request);
            System.out.println("Email sent - Status code: " + response.getStatusCode());

        } catch(IOException e){
            try {
                throw e;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
