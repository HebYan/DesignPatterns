package com.chipsandwaycool.utility;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.BodyPart;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;

/**
 * This class simplifies the processes of sending emails via the JavaMail API.
 * Construct one of these with the host name or IP of your SMTP server, and
 * then call 'sendEmail()' to fire one off!
 */
public class Emailer extends Object
  {

  private static String SMTPHost = null;
  private static String DefaultSender = null;
  
  private Session     m_session = null;

  
  
  /**
   * Set the hostname of the SMTP server instances of this class will use
   * to send emails.  This must be set prior to creating any instances.
   *
   * @param host an accessible SMTP server's host name
   */
  public static void SetSMTPHost(String host)
    {
    SMTPHost = host;
    }


  /**
   * Set an optional default sender email address, to avoid passing in that
   * value for every email sent.
   *
   * @param defaultSender a default sender email address
   */
  public static void SetDefaultSender(String defaultSender)
    {
    DefaultSender = defaultSender;
    }


  /**
   * Instantiate a new Emailer to connect with the SMTP relay 'SMTPHost'.
   * Provided this host is valid, this is all that's needed to do before
   * invoking 'sendEmail()'.
   */
  public Emailer()
    {
    if (SMTPHost == null)
      throw new NullPointerException("An Emailer instance cannot be constructed without an SMTP host.");
    }


  /**
   * Attempt to send an email for the given values, using the SMTP server
   * passed to my constructor.  Return whether the email could be sent.
   *
   * @param sender the email address of the sender
   * @param recipient the recipient email address
   * @param subject the email's subject
   * @param body the body text of the email
   * @return whether there were no problems in sending the message
   */
  public boolean sendEmail(String sender, String recipient, String subject,
        String body)
    {
    return sendEmail(sender, recipient, subject, body, null);
    }


  /**
   * Attempt to send an email for the given values, using the SMTP server
   * passed to my constructor.  Return whether the email could be sent.
   * If 'attachmentFileNames' contains any file names, add the specified
   * files as email attachments.
   *
   * @param sender the email address of the sender
   * @param recipient the recipient email address
   * @param subject the email's subject
   * @param body the body text of the email
   * @param attachmentFileNames an optional list of attachments files
   * @return whether there were no problems in sending the message
   */
  public boolean sendEmail(String sender, String recipient, String subject,
        String body, List attachmentFileNames)
    {
    if (sender == null)
      if ((sender = DefaultSender) == null)
        throw new NullPointerException("No sender email address was supplied.");
    if (m_session == null)
      {
      Properties props = new Properties();
      props.put("mail.smtp.host", SMTPHost);
      props.put("mail.smtp.localhost", SMTPHost);
      m_session = Session.getDefaultInstance(props);
      }
    try
      {
      MimeMessage message = new MimeMessage(m_session);
      message.setFrom(new InternetAddress(sender));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      message.setSubject(subject);
      if ((attachmentFileNames == null) || (attachmentFileNames.size() == 0))
        message.setText(body);
      else
        {
        Multipart multipart = new MimeMultipart();
        BodyPart part = new MimeBodyPart();
        part.setText(body);
        multipart.addBodyPart(part);
        String fileName = null;
        for (Iterator i = attachmentFileNames.iterator(); i.hasNext(); )
          {
          fileName = (String)(i.next());
          part = new MimeBodyPart();
          part.setDataHandler(new DataHandler(new FileDataSource(fileName)));
          part.setFileName(fileName);
          multipart.addBodyPart(part);
          }
        message.setContent(multipart);
        }
      Transport.send(message);
      return true;
      }
    catch (MessagingException exception)
      {
      System.out.println("A messaging exception was thrown while sending an email from '"
            + sender + "' to '" + recipient + "', subject \"" + subject + "\":");
      exception.printStackTrace();
      }
    return false;
    }

  }
