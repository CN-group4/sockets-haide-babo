import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UdpClient {

    private static final String SERVER_HOST = "100.88.190.81";
    private static final int SERVER_PORT = 5001;

    public static void main(String[] args) {
        System.out.println("[UDP Client] Pornit. Server: " + SERVER_HOST + ":" + SERVER_PORT);
        System.out.println("[UDP Client] Tu trimiți primul mesaj.\n");

        try (
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in)
        ) {
            InetAddress serverAddr = InetAddress.getByName(SERVER_HOST);

            while (true) {
                System.out.print("[You (Client)]: ");
                String mesaj = scanner.nextLine();
                byte[] sendData = (mesaj + "\n").getBytes("UTF-8");

                DatagramPacket sendPacket = new DatagramPacket(
                        sendData, sendData.length, serverAddr, SERVER_PORT
                );
                socket.send(sendPacket);

                if (mesaj.trim().equalsIgnoreCase("exit")) {
                    System.out.println("[UDP Client] Sent 'exit'. Closing.");
                    break;
                }
                
                byte[] recvBuffer = new byte[1024];
                DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
                socket.receive(recvPacket);  // blocat pana vine raspunsul

                String raspuns = new String(recvPacket.getData(), 0,
                        recvPacket.getLength(), "UTF-8").trim();
                System.out.println("[Server]: " + raspuns);

                if (raspuns.equalsIgnoreCase("exit")) {
                    System.out.println("[UDP Client] Server sent 'exit'. Closing.");
                    break;
                }
            }

        } catch (SocketException e) {
            System.err.println("[UDP Client] Socket error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[UDP Client] I/O error: " + e.getMessage());
        }

        System.out.println("[UDP Client] Done.");
    }
}
