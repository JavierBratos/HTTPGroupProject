import java.util.HashMap;
import java.util.Map;

public class API {
    private DataServer dataServer = new DataServer();
    private static final String CONTENT_TYPE_TEXT = "text/plain";
    private String server;
    private int port;

    // Constructor
    public API() {
        this.dataServer = new DataServer();
    }

    // Constructor with server and port parameters
    public API(String server, int port) {
        this.server = server;
        this.port = port;
        this.dataServer = new DataServer();
    }

    // Handle GET request
    public String handleGet(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            return formatResponse(200, dataServer.getAllAlumnos(), CONTENT_TYPE_TEXT);
        }
        return formatResponse(404, "Resource not found", CONTENT_TYPE_TEXT);
    }

    // Handle POST request
    public String handlePost(String endpoint, String body) {
        if ("/alumnos".equals(endpoint)) {
            try {
                Alumno alumno = parseAlumno(body);
                dataServer.addAlumno(alumno);
                return formatResponse(201, "Alumno added successfully", CONTENT_TYPE_TEXT);
            } catch (IllegalArgumentException e) {
                return formatResponse(400, "Bad request, invalid data format", CONTENT_TYPE_TEXT);
            }
        }
        return formatResponse(404, "Resource not found", CONTENT_TYPE_TEXT);
    }

    // Handle PUT request
    public String handlePut(String endpoint, String body) {
        try {
            if (endpoint.startsWith("/alumnos/")) {
                int id = Integer.parseInt(endpoint.split("/")[2]);
                Alumno updatedAlumno = parseAlumno(body);
                dataServer.updateAlumno(id, updatedAlumno); // No return value to check
                return formatResponse(200, "Alumno updated successfully", CONTENT_TYPE_TEXT);
            }
        } catch (IllegalArgumentException e) {
            return formatResponse(400, "Bad request, invalid data format", CONTENT_TYPE_TEXT);
        }
        return formatResponse(404, "Resource not found", CONTENT_TYPE_TEXT);
    }

    // Handle DELETE request
    public String handleDelete(String endpoint) {
        try {
            if (endpoint.startsWith("/alumnos/")) {
                int id = Integer.parseInt(endpoint.split("/")[2]);
                dataServer.removeAlumno(id); // No return value to check
                return formatResponse(200, "Alumno deleted successfully", CONTENT_TYPE_TEXT);
            }
        } catch (NumberFormatException e) {
            return formatResponse(400, "Invalid ID format", CONTENT_TYPE_TEXT);
        }
        return formatResponse(404, "Resource not found", CONTENT_TYPE_TEXT);
    }

    // Handle HEAD request
    public String handleHead(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            String data = dataServer.getAllAlumnos();
            String headers = "Content-Type: " + CONTENT_TYPE_TEXT + "\r\n" +
                    "Content-Length: " + data.getBytes().length;
            return formatResponseHeadersOnly(200, headers);
        }
        return formatResponseHeadersOnly(404, "Content-Type: " + CONTENT_TYPE_TEXT);
    }

    // Format HTTP response with body
    private String formatResponse(int statusCode, String content, String contentType) {
        return "HTTP/1.1 " + statusCode + " " + getStatusText(statusCode) + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "\r\n" +
                content;
    }

    // Format HTTP response headers only
    private String formatResponseHeadersOnly(int statusCode, String headers) {
        return "HTTP/1.1 " + statusCode + " " + getStatusText(statusCode) + "\r\n" +
                headers + "\r\n\r\n";
    }

    // Simple parser for Alumno data from a plain text string
    private Alumno parseAlumno(String data) {
        String[] parts = data.split(", ");
        Map<String, String> fields = new HashMap<>();
        for (String part : parts) {
            String[] keyValue = part.split(": ");
            fields.put(keyValue[0].trim(), keyValue[1].trim());
        }
        try {
            int id = Integer.parseInt(fields.get("id"));
            String name = fields.get("name");
            String lastname = fields.get("lastname");
            String phoneNumber = fields.get("phoneNumber");
            return new Alumno(id, name, lastname, phoneNumber);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input format");
        }
    }

    // Get HTTP status text based on code
    private String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200:
                return "OK";
            case 201:
                return "Created";
            case 400:
                return "Bad Request";
            case 404:
                return "Not Found";
            default:
                return "Error";
        }
    }
}
