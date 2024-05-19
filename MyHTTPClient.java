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
        System.out.print("Enter endpoint: ");
        String endpoint = scanner.nextLine();
        System.out.print("Enter body (if any, press Enter if none): ");
        String body = scanner.nextLine();

        APIClient client = new APIClient(server, 8080);
        String response = "";
        switch (method) {
            case "GET":
                response = client.getAlumnos();
                break;
            case "POST":
                response = client.addAlumno(body);
                break;
            case "PUT":
                response = client.updateAlumno(Integer.parseInt(endpoint.split("/")[2]), body);
                break;
            case "DELETE":
                response = client.deleteAlumno(Integer.parseInt(endpoint.split("/")[2]));
                break;
            case "HEAD":
                response = client.headAlumnos(); // Adding support for HEAD method
                break;
            default:
                System.out.println("Unsupported HTTP method.");
                break;
        }

        System.out.println(response);
        scanner.close();
    }
}
