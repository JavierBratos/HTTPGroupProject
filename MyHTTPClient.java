import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MyHTTPClient {
    private static final String API_KEY = "123";  // This should be your actual API key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter server address (e.g., httpusjproject.free.beeceptor.com): ");
        String server = scanner.nextLine();
        int port = 8080; // Standard HTTP port, change if necessary

        while (true) {
            System.out.print("Enter HTTP method (GET, POST, PUT, DELETE, HEAD): ");
            String method = scanner.nextLine().toUpperCase();

            System.out.print("Enter endpoint (e.g., /cats): ");
            String endpoint = scanner.nextLine();

            System.out.print("Enter body (if any, press Enter if none): ");
            String body = scanner.nextLine();

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json"); // Assuming JSON, change as needed
            headers.put("X-API-Key", API_KEY);

            System.out.print("Enter additional headers? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();
            while (response.equals("yes")) {
                System.out.print("Enter header key: ");
                String key = scanner.nextLine();
                System.out.print("Enter header value: ");
                String value = scanner.nextLine();
                headers.put(key, value);

                System.out.print("Add more headers? (yes/no): ");
                response = scanner.nextLine().toLowerCase();
            }

            sendRequest(server, port, method, endpoint, headers, body);

            System.out.print("Send another request? (yes/no): ");
            if (!scanner.nextLine().toLowerCase().equals("yes")) {
                break;
            }
        }
        scanner.close();
    }

    public static void sendRequest(String server, int port, String method, String endpoint, Map<String, String> headers, String body) {
        try (Socket socket = new Socket(server, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Building the request header
            StringBuilder request = new StringBuilder();
            request.append(method).append(" ").append(endpoint).append(" HTTP/1.1\r\n");
            request.append("Host: ").append(server).append("\r\n");

            // Adding headers
            for (Map.Entry<String, String> header : headers.entrySet()) {
                request.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
            }

            if (!body.isEmpty()) {
                request.append("Content-Length: ").append(body.getBytes().length).append("\r\n");
            }

            request.append("\r\n");
            request.append(body);

            // Sending the request
            out.print(request.toString());
            out.flush();

            // Reading the response
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Error in connecting or sending request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
