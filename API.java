import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class API {
    private DataServer dataServer = new DataServer(); // Instance of DataServer
    private static final String CONTENT_TYPE_JSON = "application/json";
    private String server;
    private int port;

    // Constructor for client-side usage
    public API(String server, int port) {
        this.server = server;
        this.port = port;
    }

    // Handle GET request
    public String handleGet(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            return formatResponse(200, dataServer.printAllAlumnos(), CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle POST request
    public String handlePost(String endpoint, String body) {
        if ("/alumnos".equals(endpoint)) {
            Alumno alumno = parseAlumno(body);
            dataServer.addAlumno(alumno);
            return formatResponse(201, "{\"message\":\"Alumno added successfully\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle PUT request
    public String handlePut(String endpoint, String body) {
        if (endpoint.startsWith("/alumnos/")) {
            int id = Integer.parseInt(endpoint.split("/")[2]);
            Alumno updatedAlumno = parseAlumno(body);
            dataServer.updateAlumno(id, updatedAlumno);
            return formatResponse(200, "{\"message\":\"Alumno updated successfully\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle DELETE request
    public String handleDelete(String endpoint) {
        if (endpoint.startsWith("/alumnos/")) {
            int id = Integer.parseInt(endpoint.split("/")[2]);
            dataServer.removeAlumno(id);
            return formatResponse(200, "{\"message\":\"Alumno deleted successfully\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle HEAD request
    public String handleHead(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            String data = dataServer.printAllAlumnos();
            String headers = "Content-Type: " + CONTENT_TYPE_JSON + "\r\n" +
                    "Content-Length: " + data.getBytes().length;
            return formatResponseHeadersOnly(200, headers);
        }
        return formatResponseHeadersOnly(404, "Content-Type: " + CONTENT_TYPE_JSON);
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

    // Parse JSON string to Alumno object
    private Alumno parseAlumno(String json) {
        JSONObject jsonObject = new JSONObject(json);
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String lastname = jsonObject.getString("lastname");
        String phoneNumber = jsonObject.getString("phoneNumber");
        return new Alumno(id, name, lastname, phoneNumber);
    }

    // Get HTTP status text based on code
    private String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200:
                return "OK";
            case 201:
                return "Created";
            case 404:
                return "Not Found";
            default:
                return "Error";
        }
    }
}
