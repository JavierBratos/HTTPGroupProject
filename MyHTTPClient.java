import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MyHTTPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter the full URL: ");
                String urlString = scanner.nextLine();
                System.out.print("Enter HTTP method (GET, POST, PUT, DELETE, HEAD): ");
                String method = scanner.nextLine().toUpperCase();
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(method);

                // Headers
                System.out.print("Add headers? (yes/no): ");
                String headerResponse = scanner.nextLine().toLowerCase();
                while (headerResponse.equals("yes")) {
                    System.out.print("Enter header key: ");
                    String key = scanner.nextLine();
                    System.out.print("Enter header value: ");
                    String value = scanner.nextLine();
                    connection.setRequestProperty(key, value);
                    System.out.print("Add more headers? (yes/no): ");
                    headerResponse = scanner.nextLine().toLowerCase();
                }

                // Body
                if ("POST".equals(method) || "PUT".equals(method)) {
                    connection.setDoOutput(true);
                    System.out.print("Enter body (JSON): ");
                    String body = scanner.nextLine();
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = body.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }
                }

                connection.connect();

                // Read response
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                System.out.println("Response: " + response.toString());
                System.out.println("Response Code: " + connection.getResponseCode());
                System.out.println("Response Message: " + connection.getResponseMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.print("Send another request? (yes/no): ");
            if (!scanner.nextLine().toLowerCase().equals("yes")) {
                break;
            }
        }
        scanner.close();
    }
}