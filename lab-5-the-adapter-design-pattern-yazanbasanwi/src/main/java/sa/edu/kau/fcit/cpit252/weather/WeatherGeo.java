package sa.edu.kau.fcit.cpit252.weather;

public interface WeatherGeo {
    // Get weather data by the geographical coordinates (latitude, longitude).
 WeatherInfo getWeatherInfo(double lat, double lon);
}
