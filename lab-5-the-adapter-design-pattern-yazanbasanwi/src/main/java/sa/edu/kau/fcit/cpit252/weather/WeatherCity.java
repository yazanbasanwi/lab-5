package sa.edu.kau.fcit.cpit252.weather;


public interface WeatherCity {
    // Get weather data by the city name.
     WeatherInfo getWeatherInfo(String cityName);
}
