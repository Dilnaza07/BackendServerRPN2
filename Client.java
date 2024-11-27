import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Client implements Runnable {
    private final Socket socket;
    private Thread thread;

    public Client(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
    }

    public void run() {

        System.out.println(socket);

        try {
            InputStream in = socket.getInputStream();
            while (true){
               int unicode = in.read();
                char symbol = (char) unicode;
                System.out.print(symbol);
                if(in.available() == 0) {
                    break;
                }
            }
            System.out.println("123");
         //   String message = "<a href=\"/login\">Hello world!</a>";
            String message =
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: 57\r\n" +
                            "\r\n" +
                            "<html><body><a href=\"/login\">Hello world!</a></body></html>";

            OutputStream out = socket.getOutputStream();
            out.write(message.getBytes());
            out.flush();

            socket.close();


        } catch (IOException ioe) {
            System.out.println("Error" + ioe);
        }

//        System.out.println("------------------------------------------------");
//        System.out.println("[ " + socket + " ];");
//        String text = readData();
//        System.out.println(text);
//
//        text = text.substring(7, text.length());
//        java.util.List<String> postfixForm = ToPostfixParser.toPostfix(text);
//        String listString = String.join(" ", postfixForm);
//        sendMessage("" + listString);
//
//        String postfixFormString = readData();
//        System.out.println(postfixFormString);
//        Double result = RPNCalculator.calculate(postfixForm);
//        sendMessage("" + result);
//        try {
//            socket.close();
//        } catch (IOException ioe) {
//            System.out.println("Socket Error " + ioe);
//        }
//        System.out.println("------------------------------------------------");
    }

    public void go() {
        thread.start();
    }

    private void sendMessage(String text) {
        byte[] array = text.getBytes();
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(array);
            outputStream.flush();
        } catch (IOException ioe) {
            System.out.println("Client Error " + ioe);
        }
    }

    private String readData() {
        try {
            String text = "";
            InputStream input = socket.getInputStream();
            while(true) {
                int unicode = input.read();
                char symbol = (char)unicode;
                text = text + symbol;
                if(input.available() == 0) {
                    break;
                }
            }
            return text;
        } catch (IOException ioe) {
            System.out.println("Client error " + ioe);
            return null;
        }
    }
}
