package com.group3.fams.serviceImpls;

import com.group3.fams.entity.EmailDetails;
import com.group3.fams.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
  MimeMessage mimeMessage;

  private final JavaMailSender javaMailSender;

  @Value("nmtrieu762@gmail.com")
  private String sender;

  @Override
  public String sendMailtoNewPassword(EmailDetails details) {
    try {
      mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

      mimeMessageHelper.setFrom(sender);
      mimeMessageHelper.setTo(details.getRecipient());
      mimeMessageHelper.setSubject(details.getSubject());
      mimeMessageHelper.setText(readFormat(details.getMsgBody(),"@@@@@@@@@######", "MailPasswordFormat.txt"), true);

      javaMailSender.send(mimeMessage);

      return "Mail sent successfully";
    } catch (Exception e) {
      return "Mail sent fail" + e.toString();
    }
  }

  @Override
  public String sendMailtoChangePassword(EmailDetails details) {
    try {
      mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

      mimeMessageHelper.setFrom(sender);
      mimeMessageHelper.setTo(details.getRecipient());
      mimeMessageHelper.setSubject(details.getSubject());
      mimeMessageHelper.setText(readFormat(details.getMsgBody(),"@@@@@@@@@######", "MailChangePasswordFormat.txt"), true);

      javaMailSender.send(mimeMessage);

      return "Mail sent successfully";
    } catch (Exception e) {
      return "Mail sent fail" + e.toString();
    }
  }

//  public String readFormat(String content, String replaceContent, String emailTemplateFile) throws IOException {
//    // Use the class loader to load the file from the resources folder
//    // String filePath = "com/group3/fams/files/" + emailTemplateFile;
//    // InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
//
//    // Correct the path and use the correct separator
//    Path path = Paths.get("com/group3/fams/files/", emailTemplateFile);
//    // Argument to create MultipartFile
//    InputStream input = new FileInputStream(path.toFile());
//    System.out.println(input);
//
//    if (input == null) {
//      throw new IOException("File not found: " + emailTemplateFile);
//    }
//
//    try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
//      System.out.println(br);
//      StringBuilder result = new StringBuilder();
//      String line;
//      while ((line = br.readLine()) != null) {
//        result.append(line).append("\n");
//      }
//
//      String formattedEmail = result.toString().replace(replaceContent, content);
//      return formattedEmail;
//    }
//  }
public String readFormat(String content, String replaceContent, String emailTemplateFile) throws IOException {
    // Sử dụng class loader để tải file từ thư mục resources
    //  InputStream inputStream =
    // getClass().getClassLoader().getResourceAsStream("com\\group3\\fams\\files\\" +
    // emailTemplateFile);

    Path path =
        Paths.get(
            "./files/MailPasswordFormat.txt");
  InputStream inputStream = null;
  try {
    File file = path.toFile();
//    inputStream = new FileInputStream(file.getAbsoluteFile());
     inputStream = getClass().getResourceAsStream("MailPasswordFormat.txt");
      System.out.println(inputStream);
  } catch (Exception e) {
      System.out.println("Error: " + e);
  }

  if (inputStream == null) {
    throw new IOException("File not found: " + emailTemplateFile);
  }

  try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
    StringBuilder result = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      result.append(line).append("\n");
    }

    String formattedEmail = result.toString().replace(replaceContent, content);
    return formattedEmail;
  }
}


  public static void main(String[] args) throws Exception{
    //    Path p = Paths.get("/fams/files/MailPasswordFormat.txt");
    Path path =
        Paths.get(
            System.getProperty("user.dir") + "\\test2\\fams\\files\\MailPasswordFormat.txt");

//    String path = System.getProperty("user.dir")  + p;
    System.out.println(path);
    File f = new File(path.toString());
    System.out.println(f.isFile());
  }
}
