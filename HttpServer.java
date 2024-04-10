import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int DEFAULT_PORT = 8080; // Default port number
    private static final String EXPECTED_API_KEY = "123"; // Change this to your expected API key

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        HttpServer server = new HttpServer();
        server.start(port);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            ExecutorService executorService = Executors.newCachedThreadPool();

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new RequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestHandler implements Runnable {
        private Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

                // Read the request
                StringBuilder request = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    request.append(line).append("\r\n");
                }

                // Process the request and generate response
                String response;
                if (validateApiKey(request.toString())) {
                    response = processRequest(request.toString());
                } else {
                    response = "HTTP/1.1 401 Unauthorized\r\nContent-Type: text/plain\r\n\r\nUnauthorized: Invalid API Key";
                }

                // Send the response
                writer.println(response);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean validateApiKey(String request) {
            // Extract the API key from the request header
            String[] lines = request.split("\r\n");
            for (String line : lines) {
                if (line.startsWith("X-API-Key:")) {
                    String apiKey = line.substring("X-API-Key:".length()).trim();
                    return apiKey.equals(EXPECTED_API_KEY);
                }
            }
            return false;
        }

        private String processRequest(String request) {
            // Process the request here according to the server's logic
            // For this example, just return a simple response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHello from server!";
        }
    }
}
