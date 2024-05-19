import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class API {
    private static DataServer dataServer = new DataServer(); // Data server instance.
    private static final String CONTENT_TYPE_JSON = "application/json";
    private String server;
    private int port;

    // Constructor for client-side usage
    public API(String server, int port) {
        this.server = server;
        this.port = port;
    }

    // Server-side Methods

    // Handle GET request
    public static String handleGet(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            return formatResponse(200, dataServer.printAllAlumnos(), CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle POST request
    public static String handlePost(String endpoint, String body) {
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
            dataServer.updateAlumno(updatedAlumno);
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

    // Client-side Methods

    // Get all Alumnos
    public String getAlumnos() {
        return sendRequest("GET", "/alumnos", null, new HashMap<>());
    }

    // Add a new Alumno
    public String addAlumno(String json) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", CONTENT_TYPE_JSON);
        return sendRequest("POST", "/alumnos", json, headers);
    }

    // Update an existing Alumno
    public String updateAlumno(int id, String json) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", CONTENT_TYPE_JSON);
        return sendRequest("PUT", "/alumnos/" + id, json, headers);
    }

    // Delete an Alumno
    public String deleteAlumno(int id) {
        return sendRequest("DELETE", "/alumnos/" + id, null, new HashMap<>());
    }

    // Utility Methods

    // Format HTTP response
    private static String formatResponse(int statusCode, Object object, String contentType) {
        return "HTTP/1.1 " + statusCode + " " + getStatusText(statusCode) + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "\r\n" +
                object;
    }

    // Parse JSON string to Alumno object
    private static Alumno parseAlumno(String json) {
        JSONObject jsonObject = new JSONObject(json);
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String lastname = jsonObject.getString("lastname");
        String phoneNumber = jsonObject.getString("phoneNumber");
        return new Alumno(id, name, lastname, phoneNumber);
    }

    // Send HTTP request (placeholder for client HTTP interaction)
    private String sendRequest(String method, String endpoint, String body, Map<String, String> headers) {
        // Assuming MyHTTPClient has a static method sendRequest to handle HTTP requests
        MyHTTPClient.sendRequest(this.server, this.port, method, endpoint, headers, body);
        return "Response handled";
    }

    // Get HTTP status text based on code
    private static String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200: return "OK";
            case 201: return "Created";
            case 404: return "Not Found";
            default:  return "Error";
        }
    }
}

