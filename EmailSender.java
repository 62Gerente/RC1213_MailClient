import java.io.*;
import java.net.*;
import java.util.Scanner;


public class EmailSender
{
   private static Socket socket;
   private static Scanner scanner;

   public static void main(String[] args) throws Exception{
     socket = new Socket("gmail-smtp-in.l.google.com", 25);  
      scanner = new Scanner(System.in);
      System.out.print("\033[H\033[2J");
      System.out.flush();
      // Create a BufferedReader to read a line at a time.
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      // Read greeting from the server.
      String response = br.readLine();
      System.out.println("Tentativa de conexão...");
      System.out.println("\t<"+response);
      if (!response.startsWith("220")) {
         throw new Exception("220 reply not received from server.");
      }

      // Get a reference to the socket's output stream.
      OutputStream os = socket.getOutputStream();

      // Send HELO command and get server response.
      String command = "HELO alice\r\n";
      System.out.print(command);
      os.write(command.getBytes("US-ASCII"));
      os.flush();
      response = br.readLine();
      System.out.println("\t<"+response);
      if (!response.startsWith("250")) {
         throw new Exception("250 reply not received from server.");
      }
             
      // Send MAIL FROM command.
      String mailFrom = "MAIL FROM:<teste.testes> \r\n";
      System.out.print(mailFrom);
      os.write(mailFrom.getBytes("US-ASCII"));
      os.flush();
      response = br.readLine();
      System.out.println("\t<"+response);
      if (!response.startsWith("250")) {
         throw new Exception("250 reply not received from server.");
      }

      // Send RCPT TO command.
      System.out.print("\n##INTRODUZA O USERNAME DO UTILIZADOR GMAIL A QUEM PRETENDE ENVIAR UM EMAIL:\n>");
      String gmail = scanner.nextLine();
      System.out.println("");
      String rctpTo = "RCPT TO:<"+gmail+"@gmail.com>\r\n";
      System.out.print(rctpTo);
      os.write(rctpTo.getBytes("US-ASCII"));
      os.flush();
      response = br.readLine();
      System.out.println("\n\t<"+response);
      if (!response.startsWith("250")) {
         throw new Exception("250 reply not received from server.");
      }
      
      // Send DATA command.
      String data = "DATA\r\n";
      System.out.print(data);
      os.write(data.getBytes("US-ASCII"));
      os.flush();
      response = br.readLine();
      if (!response.startsWith("354")) {
          throw new Exception("354 reply not received from server.");
       }
   

      // Send message data.  
      System.out.print("\n##INTRODUZA O ASSUNTO DA MENSAGEM:\n>");
      String assunto = scanner.nextLine();
      System.out.println("");
      String subject = "SUBJECT: TESTE:"+assunto+"\r\n\r\n";
      System.out.print("##INTRODUZA O CONTEÚDO DA MENSAGEM:\n>");
      String texto = scanner.nextLine();
      System.out.println("\n**************************");
      String text = subject+texto+"\r\n";
      System.out.print(text);
      os.write(text.getBytes("US-ASCII"));
      os.flush();
    
      
      // End with line with a single period.
      String end = ".\r\n";
      System.out.print(end);
      System.out.println("**************************\n");
      os.write(end.getBytes("US-ASCII"));
      os.flush();
      response = br.readLine();
      System.out.println("\t<"+response);
      if (!response.startsWith("250")) {
         throw new Exception("250 reply not received from server.");
      }

      // Send QUIT command.
      String quit = "QUIT\r\n";
      System.out.print(quit);
      os.write(quit.getBytes("US-ASCII"));
      os.flush();
      response = br.readLine();
      System.out.println("\t<"+response+"\nEMAIL ENVIADO COM SUCESSO\n\n");
      if (!response.startsWith("221")) {
         throw new Exception("221 reply not received from server.");
      }
   
   }
}