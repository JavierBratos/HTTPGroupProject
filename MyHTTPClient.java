
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MyHTTPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for server address
        System.out.print("Enter server address: ");
        String server = scanner.nextLine();

        // HTTP method
        System.out.print("Enter HTTP method (GET, POST, PUT, DELETE, HEAD): ");
        String method = scanner.nextLine().toUpperCase();

        // Endpoint
        System.out.print("Enter endpoint: ");
        String endpoint = scanner.nextLine();

        // Body
        System.out.print("Enter body (if any, press Enter if none): ");
        String body = scanner.nextLine();

        // Custom headers
        Map<String, String> headers = new HashMap<>();
        System.out.print("Enter x-api-key header value: ");
        String apiKey = scanner.nextLine();
        headers.put("x-api-key", apiKey);
        System.out.print("Enter x-forwarded-host header value: ");
        String forwardedHost = scanner.nextLine();
        headers.put("x-forwarded-host", forwardedHost);
        System.out.print("Enter x-forwarded-proto header value: ");
        String forwardedProto = scanner.nextLine();
        headers.put("x-forwarded-proto", forwardedProto);

        // Always use port 80
        int port = 8080;

        // Send the request
        sendRequest(server, port, method, endpoint, headers, body);
        
        scanner.close();
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
            if (!body.isEmpty()) {
                requestBuilder.append("Content-Length: ").append(body.length()).append("\r\n");
            }
            requestBuilder.append("Content-Type: text/plain\r\n"); 
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
