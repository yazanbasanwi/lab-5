package sa.edu.kau.fcit.cpit252.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPHelper {
    public static HttpResponse<String> sendGet(URI uri){
        try {
            // create a client
            HttpClient httpClient = HttpClient.newHttpClient();
            // create an HTTP GET request
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("accept", "application/json")
                    .build();
            // Send the HTTP request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // Test if the response from the server is successful
            if (response.statusCode() !=200) {
                System.err.println(response.statusCode());
                System.err.println(response.body());
                return null;
            }
            return response;
        }
        catch(ConnectException e){
            System.err.println("ERROR: You must have an Internet connection to call the API");
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
