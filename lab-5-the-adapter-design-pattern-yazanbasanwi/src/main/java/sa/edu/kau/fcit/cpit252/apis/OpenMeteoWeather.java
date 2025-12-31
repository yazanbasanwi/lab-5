package sa.edu.kau.fcit.cpit252.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sa.edu.kau.fcit.cpit252.utils.HTTPHelper;
import sa.edu.kau.fcit.cpit252.weather.WeatherGeo;
import sa.edu.kau.fcit.cpit252.weather.WeatherInfo;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/* This is the adaptee class, OpenMeteoWeather, which takes a geographic coordinates of a city and returns the daily weather forecasts.
 * The adaptee is a class that has an incompatible interface the client can't work with.
 */
public class OpenMeteoWeather implements WeatherGeo {

    private final String API_URL = "https://api.open-meteo.com/v1/forecast";
    // Returns 10 days weather forecasts for the given geo coordinates
    @Override
    public WeatherInfo getWeatherInfo(double latitude, double longitude) {
        // build the URL
        WeatherInfo wInfo = null;
        try {
            // Build the URL for the API endpoint
            URI uri = new URIBuilder(API_URL)
                    .addParameter("latitude", String.format("%,.2f", latitude))
                    .addParameter("longitude", String.format("%,.2f", longitude))
                    .addParameter("timezone", "Asia/Kuwait")
                    .addParameter("daily", "temperature_2m_max,temperature_2m_min")
                    .build();
            HttpResponse<String> response = HTTPHelper.sendGet(uri);
            if (response != null) {
                wInfo = parseWeatherResponse(response.body(), WeatherInfo.class);
                return wInfo;
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static WeatherInfo parseWeatherResponse(String responseString, Class<?> elementClass){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode weatherInfoNode = objectMapper.readTree(responseString);
            WeatherInfo wInfo = new WeatherInfo();
            // Get latitude and longitude
            wInfo.setLatitude(weatherInfoNode.get("latitude").doubleValue());
            wInfo.setLongitude(weatherInfoNode.get("longitude").doubleValue());
            // Get the dates for the seven days forecasts
            Iterator<JsonNode>  datesIterator = weatherInfoNode.get("daily").get("time").elements();
            ArrayList<LocalDate> dates = new ArrayList<>();
            while(datesIterator.hasNext()){
                dates.add(LocalDate.parse(datesIterator.next().asText()));
            }
            wInfo.setDates(dates);
            // Get max temps
            JsonNode maxTempsNodes= weatherInfoNode.get("daily").get("temperature_2m_max");
            int []maxTemps = new int[maxTempsNodes.size()];
            for (int i=0; i< maxTemps.length; i++) {
                maxTemps[i] = (int) Math.round(Double.valueOf(maxTempsNodes.get(i).toString()));
            }
            wInfo.setMaxTemps(maxTemps);
            // Get min temps
            JsonNode minTempsNodes= weatherInfoNode.get("daily").get("temperature_2m_min");
            int []minTemps = new int[minTempsNodes.size()];
            for (int i=0; i< maxTemps.length; i++) {
                minTemps[i] = (int) Math.round(Double.valueOf(minTempsNodes.get(i).toString()));
            }
            wInfo.setMinTemps(minTemps);
            return wInfo;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

