import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class MyHTTPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server address: ");
        String server = scanner.nextLine();
        System.out.print("Enter HTTP method (GET, POST, PUT, DELETE, HEAD): ");
        String method = scanner.nextLine().toUpperCase();
        System.out.print("Enter endpoint (e.g., /alumnos or /alumnos/1): ");
        String endpoint = scanner.nextLine();
        System.out.print("Enter body (if any, press Enter if none): ");
        String body = scanner.nextLine();

        API client = new API(server, 8080);
        String response = "";
        try {
            int id = endpoint.startsWith("/alumnos/") ? Integer.parseInt(endpoint.split("/")[2]) : -1;
            switch (method) {
                case "GET":
                    response = client.handleGet(endpoint);
                    break;
                case "POST":
                    response = client.handlePost(endpoint,body);
                    break;
                case "PUT":
                    if(id != -1) {
                        response = client.handlePut(endpoint, body);
                    } else {
                        response = "Invalid endpoint for PUT request.";
                    }
                    break;
                case "DELETE":
                    if(id != -1) {
                        response = client.handleDelete(endpoint);
                    } else {
                        response = "Invalid endpoint for DELETE request.";
                    }
                    break;
                case "HEAD":
                    response = client.handleHead(endpoint);
                    break;
                default:
                    response = "Unsupported HTTP method.";
                    break;
            }
        } catch (NumberFormatException e) {
            response = "Error parsing ID in endpoint.";
        }

        System.out.println(response);
        scanner.close();
    }
}