/* 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int DEFAULT_PORT = 80; // Default port number
    private static final String EXPECTED_API_KEY = "123"; // Change this to your expected API key

    // Map to store registered handlers for different HTTP methods and endpoints
    private Map<String, Map<String, Handler>> routeHandlers = new HashMap<>();

    // Register handlers for different HTTP methods and endpoints
    public void on(String method, String endpoint, Handler handler) {
        Map<String, Handler> methodHandlers = routeHandlers.getOrDefault(method, new HashMap<>());
        methodHandlers.put(endpoint, handler);
        routeHandlers.put(method, methodHandlers);
    }

    // Start the server
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            ExecutorService executorService = Executors.newCachedThreadPool();

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new RequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handler for incoming requests
    private class RequestHandler implements Runnable {
        private Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

                // Read the request
                StringBuilder request = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    request.append(line).append("\r\n");
                }

                // Extract method and endpoint from the request
                String[] requestLines = request.toString().split("\r\n");
                String[] firstLineParts = requestLines[0].split(" ");
                String method = firstLineParts[0];
                String endpoint = firstLineParts[1];

                // Find handler for the requested method and endpoint
                Handler handler = routeHandlers.getOrDefault(method, new HashMap<>()).get(endpoint);

                // Process the request and generate response
                String response;
                if (handler != null && validateApiKey(request.toString())) {
                    response = handler.handle(request.toString());
                } else {
                    response = "HTTP/1.1 401 Unauthorized\r\nContent-Type: text/plain\r\n\r\nUnauthorized: Invalid API Key";
                }

                // Print request information
                System.out.println("Request handled:");
                System.out.println(request.toString());

                // Send the response
                writer.println(response);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Handler interface
    public interface Handler {
        String handle(String request);
    }

    // Main method to start the server
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        HttpServer server = new HttpServer();

        // Register handlers for different endpoints and methods
        server.on("GET", "/cats", request -> {
            // Process GET /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling GET /cats request\n" + request;
        });

        server.on("POST", "/cats", request -> {
            // Process POST /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling POST /cats request\n" + request;
        });

        server.on("PUT", "/cats", request -> {
            // Process PUT /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling PUT /cats request\n" + request;
        });

        server.on("DELETE", "/cats", request -> {
            // Process DELETE /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling DELETE /cats request\n" + request;
        });

        server.on("HEAD", "/cats", request -> {
            // Process HEAD /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling HEAD /cats request\n" + request;
        });

        // Start the server
        server.start(port);
    }

    // API key validation method
    private boolean validateApiKey(String request) {
        // Extract the API key from the request header
        String[] lines = request.split("\r\n");
        for (String line : lines) {
            if (line.startsWith("X-API-Key:")) {
                String apiKey = line.substring("X-API-Key:".length()).trim();
                return apiKey.equals(EXPECTED_API_KEY);
            }
        }
        return false;
    }
}
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final int DEFAULT_PORT = 80; // Default port number
    private static final String EXPECTED_API_KEY = "a123"; // Change this to your expected API key

    // Map to store registered handlers for different HTTP methods and endpoints
    private Map<String, Map<String, Handler>> routeHandlers = new HashMap<>();

    // Register handlers for different HTTP methods and endpoints
    public void on(String method, String endpoint, Handler handler) {
        Map<String, Handler> methodHandlers = routeHandlers.getOrDefault(method, new HashMap<>());
        methodHandlers.put(endpoint, handler);
        routeHandlers.put(method, methodHandlers);
    }

    // Start the server
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            ExecutorService executorService = Executors.newCachedThreadPool();

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new RequestHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handler for incoming requests
    private class RequestHandler implements Runnable {
        private Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream())) {

                // Read the request
                StringBuilder request = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    request.append(line).append("\r\n");
                }

                // Extract method and endpoint from the request
                String[] requestLines = request.toString().split("\r\n");
                String[] firstLineParts = requestLines[0].split(" ");
                String method = firstLineParts[0];
                String endpoint = firstLineParts[1];

                // Extract body and content type from the request
                String body = extractBody(request.toString());

                // Find handler for the requested method and endpoint
                Handler handler = routeHandlers.getOrDefault(method, new HashMap<>()).get(endpoint);

                // Process the request and generate response
                String response;
                if (handler != null && validateApiKey(request.toString())) {
                    response = handler.handle(request.toString());
                } else {
                    response = "HTTP/1.1 401 Unauthorized\r\nContent-Type: text/plain\r\n\r\nUnauthorized: Invalid API Key";
                }

                // Print request information
                System.out.println("Request handled:");
                System.out.println("Method: " + method);
                System.out.println("Endpoint: " + endpoint);
                System.out.println("Body: " + body);
                System.out.println(request.toString());

                // Send the response
                writer.println(response);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String extractBody(String request) {
            int bodyIndex = request.indexOf("\r\n\r\n");
            if (bodyIndex != -1 && bodyIndex + 4 < request.length()) {
                return request.substring(bodyIndex + 4);
            }
            return "";
        }
    }

    // Handler interface
    public interface Handler {
        String handle(String request);
    }

    // Main method to start the server
    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        HttpServer server = new HttpServer();

        // Register handlers for different endpoints and methods
        server.on("GET", "/cats", request -> {
            // Process GET /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling GET /cats request\n" + request;
        });

        server.on("POST", "/cats", request -> {
            // Process POST /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling POST /cats request\n" + request;
        });

        server.on("PUT", "/cats", request -> {
            // Process PUT /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling PUT /cats request\n" + request;
        });

        server.on("DELETE", "/cats", request -> {
            // Process DELETE /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling DELETE /cats request\n" + request;
        });

        server.on("HEAD", "/cats", request -> {
            // Process HEAD /cats request and append request info to response
            return "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHandling HEAD /cats request\n" + request;
        });

        // Start the server
        server.start(port);
    }

    // API key validation method
    private boolean validateApiKey(String request) {
        // Extract the API key from the request header
        String[] lines = request.split("\r\n");
        for (String line : lines) {
            if (line.startsWith("X-API-Key:")) {
                String apiKey = line.substring("X-API-Key:".length()).trim();
                return apiKey.equals(EXPECTED_API_KEY);
            }
        }
        return false;
    }
}