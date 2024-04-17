import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyHTTPClient {
    private static final String API_KEY = "123"; // Change this to your API key

    public static void main(String[] args) {
        String server = "httpusjproject.free.beeceptor.com"; // Change this to your server address
        int port = 80; // Change this to your server port

        // HTTP request parameters
        String method = "POST"; // Change this to any HTTP method you want to use
        String endpoint = "/cats"; // Change this to the desired endpoint
        String body = "{\"name\": \"Hercules\", \"breed\": \"European\", \"age\": 3}"; // Example request body

        // Custom headers including API key
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-API-Key", API_KEY);

        sendRequest(server, port, method, endpoint, headers, body);
    }

    public static void sendRequest(String server, int port, String method, String endpoint, Map<String, String> headers, String body) {
        try (Socket socket = new Socket(server, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Construct the HTTP request
            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append(method).append(" ").append(endpoint).append(" HTTP/1.1\r\n");
            requestBuilder.append("Host: ").append(server).append("\r\n");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
            }
            requestBuilder.append("Content-Length: ").append(body.length()).append("\r\n");
            requestBuilder.append("\r\n");
            requestBuilder.append(body);

            // Send the request
            out.println(requestBuilder.toString());

            // Read and print the response
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
