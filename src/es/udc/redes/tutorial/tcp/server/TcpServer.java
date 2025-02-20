package es.udc.redes.tutorial.tcp.server;

import java.net.*;
import java.io.*;

/**
 * Monothread TCP echo server.
 */
public class TcpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.tcp.server.TcpServer <port>");
            System.exit(-1);
        }

        ServerSocket socketServer = null;
        Socket socketClient = null;
        BufferedReader breader = null;
        PrintWriter pwriter = null;

        int port = Integer.parseInt(argv[0]);

        try {
            // Create a server socket
            socketServer = new ServerSocket(port);
            // Set a timeout of 300 secs
            socketServer.setSoTimeout(300000);

            while (true) {
                // Wait for connections
                socketClient = socketServer.accept();
                // Set the input channel
                breader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                // Set the output channel
                pwriter = new PrintWriter(socketClient.getOutputStream(), true);
                // Receive the client message
                String mensaje = breader.readLine();
                // Send response to the client
                pwriter.println(mensaje);
                // Close the streams
                if (breader != null) {
                    breader.close();
                }

                if (pwriter != null) {
                    pwriter.close();
                }
            }

        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs ");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
//Close the socket
            try {
                if (socketServer != null) {
                    socketServer.close();
                }
                if (socketClient != null) {
                    socketClient.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}