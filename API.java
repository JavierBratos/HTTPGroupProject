/*import org.json.JSONObject;

public class API {
    private DataServer dataServer = new DataServer(); // Instance of DataServer
    private static final String CONTENT_TYPE_JSON = "application/json";
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
            return formatResponse(200, dataServer.getAllAlumnos(), CONTENT_TYPE_JSON);
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
            String data = dataServer.getAllAlumnos();
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

    public String getAlumnos() {
        return handleGet("/alumnos");
    }

    public String addAlumno(String body) {
        return handlePost("/alumnos", body);
    }

    public String updateAlumno(int id, String body) {
        return handlePut("/alumnos/" + id, body);
    }

    public String deleteAlumno(int id) {
        return handleDelete("/alumnos/" + id);
    }

    public String headAlumnos() {
        return handleHead("/alumnos");
    }
}
*/
import java.util.HashMap;
import java.util.Map;

public class API {
    private DataServer dataServer = new DataServer();
    private static final String CONTENT_TYPE_JSON = "application/json";
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
            return formatResponse(200, dataServer.getAllAlumnos(), CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle POST request
    public String handlePost(String endpoint, String body) {
        if ("/alumnos".equals(endpoint)) {
            try {
                Alumno alumno = parseAlumno(body);
                dataServer.addAlumno(alumno);
                return formatResponse(201, "{\"message\":\"Alumno added successfully\"}", CONTENT_TYPE_JSON);
            } catch (IllegalArgumentException e) {
                return formatResponse(400, "{\"error\":\"Bad request, invalid data format\"}", CONTENT_TYPE_JSON);
            }
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle PUT request
    public String handlePut(String endpoint, String body) {
        try {
            if (endpoint.startsWith("/alumnos/")) {
                int id = Integer.parseInt(endpoint.split("/")[2]);
                Alumno updatedAlumno = parseAlumno(body);
                dataServer.updateAlumno(id, updatedAlumno); // No return value to check
                return formatResponse(200, "{\"message\":\"Alumno updated successfully\"}", CONTENT_TYPE_JSON);
            }
        } catch (IllegalArgumentException e) {
            return formatResponse(400, "{\"error\":\"Bad request, invalid data format\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle DELETE request
    public String handleDelete(String endpoint) {
        try {
            if (endpoint.startsWith("/alumnos/")) {
                int id = Integer.parseInt(endpoint.split("/")[2]);
                dataServer.removeAlumno(id); // No return value to check
                return formatResponse(200, "{\"message\":\"Alumno deleted successfully\"}", CONTENT_TYPE_JSON);
            }
        } catch (NumberFormatException e) {
            return formatResponse(400, "{\"error\":\"Invalid ID format\"}", CONTENT_TYPE_JSON);
        }
        return formatResponse(404, "{\"error\":\"Resource not found\"}", CONTENT_TYPE_JSON);
    }

    // Handle HEAD request
    public String handleHead(String endpoint) {
        if ("/alumnos".equals(endpoint)) {
            String data = dataServer.getAllAlumnos();
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

    // Simple parser for Alumno data from a formatted string
    private Alumno parseAlumno(String data) {
        String[] parts = data.replaceAll("[{}\"]", "").split(",");
        Map<String, String> fields = new HashMap<>();
        for (String part : parts) {
            String[] keyValue = part.split(":");
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
            case 200: return "OK";
            case 201: return "Created";
            case 400: return "Bad Request";
            case 404: return "Not Found";
            default: return "Error";
        }
    }
}

