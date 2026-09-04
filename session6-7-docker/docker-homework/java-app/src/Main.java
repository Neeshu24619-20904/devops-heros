package src;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/", (HttpExchange exchange) -> {

            String response = """
                <html>
                    <head>
                        <title>Java Docker App</title>
                    </head>
                    <body>
                        <h1>Hello World from Java + Docker!</h1>
                    </body>
                </html>
                """;

            exchange.sendResponseHeaders(200, response.length());

            OutputStream outputStream = exchange.getResponseBody();

            outputStream.write(response.getBytes());

            outputStream.close();
        });

        server.start();

        System.out.println("Java server running on port 8080");
    }
}