import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpClient {

    private static final String SERVER_HOST = "100.88.190.81";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        System.out.println("[TCP Client] Connecting to " + SERVER_HOST + ":" + SERVER_PORT + "...");

        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), "UTF-8"));
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("[TCP Client] Connected. Send first message.\n");

            while (true) {
                // --- PASUL 1: Clientul trimite mesaj ---
                System.out.print("[You (Client)]: ");
                String mesaj = scanner.nextLine();
                out.println(mesaj);  // trimite cu newline (readline() pe server)

                if (mesaj.trim().equalsIgnoreCase("exit")) {
                    System.out.println("[TCP Client] Sent 'exit'. Closing connection.");
                    break;
                }

                // --- PASUL 2: Clientul asteapta raspunsul serverului ---
                String raspuns = in.readLine();
                if (raspuns == null) {
                    System.out.println("[TCP Client] Server shut down connection.");
                    break;
                }

                System.out.println("[Server]: " + raspuns);

                if (raspuns.trim().equalsIgnoreCase("exit")) {
                    System.out.println("[TCP Client] Server sent 'exit'. Shutting down.");
                    break;
                }
            }

        } catch (ConnectException e) {
            System.err.println("[TCP Client] Can't connect to server.");
        } catch (IOException e) {
            System.err.println("[TCP Client] Error I/O: " + e.getMessage());
        }

        System.out.println("[TCP Client] Done.");
    }
}