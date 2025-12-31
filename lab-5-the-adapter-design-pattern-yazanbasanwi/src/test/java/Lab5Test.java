import org.junit.jupiter.api.Test;
import sa.edu.kau.fcit.cpit252.apis.OpenMeteoWeather;
import sa.edu.kau.fcit.cpit252.geoLocation.GeoLocationService;
import sa.edu.kau.fcit.cpit252.geoLocation.Location;
import sa.edu.kau.fcit.cpit252.weather.WeatherAdapter;
import sa.edu.kau.fcit.cpit252.weather.WeatherCity;
import sa.edu.kau.fcit.cpit252.weather.WeatherGeo;
import sa.edu.kau.fcit.cpit252.weather.WeatherInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Lab5Test {

    // WeatherGeo interface should have one method that takes two parameters latitude and longitude of type double
    // and returns a WeatherInfo object
    @Test
    public void assertWeatherGeoInterface() {
        assertEquals(1, WeatherGeo.class.getDeclaredMethods().length);
        Method method = WeatherGeo.class.getDeclaredMethods()[0];
        assertEquals(WeatherInfo.class, method.getReturnType());
        assertEquals(2, method.getParameters().length);
        for (Parameter p : method.getParameters()) {
            assertEquals(double.class.getName(), p.getType().getTypeName());
        }
    }

    // WeatherCity interface should have one method that takes one parameter (city name) of type String
    // and returns a WeatherInfo object
    @Test
    public void assertWeatherCityInterface() {
        assertEquals(1, WeatherCity.class.getDeclaredMethods().length);
        Method method = WeatherCity.class.getDeclaredMethods()[0];
        assertEquals(WeatherInfo.class, method.getReturnType());
        assertEquals(1, method.getParameters().length);
        assertEquals(String.class.getName(), method.getParameters()[0].getType().getTypeName());
    }


    // Should have an adapter class, WeatherAdapter, that implements the WeatherCity interface
    @Test
    public void assertAdapterImplementsInterfaces() {
        List<Class<?>> interfaces = Arrays.asList(WeatherAdapter.class.getInterfaces());
        assertEquals(1, interfaces.size());
        assertTrue(interfaces.contains(WeatherCity.class));
    }

    // Should have an adapter class, WeatherAdapter, that has a method that takes city name and returns weather info
    @Test
    public void assertAdapterMethod() {
        boolean passed = false;
        for (Method method : WeatherAdapter.class.getDeclaredMethods()) {
            if (method.getParameterCount() == 1 && method.getName().equalsIgnoreCase("getWeatherInfo")) {
                assertEquals(1, method.getParameters().length);
                assertEquals(method.getReturnType().getTypeName(), WeatherInfo.class.getName());
                passed = true;
            }
        }
        assertTrue(passed);
    }

    // assert that OpenMeteo Weather API is working
    @Test
    public void testOpenMeteoWeatherAPI() {
        OpenMeteoWeather openMeteoWeather = new OpenMeteoWeather();
        WeatherInfo weatherInfo = openMeteoWeather.getWeatherInfo(52.52, 13.41);
        assertNotNull(weatherInfo);
        assertTrue(weatherInfo.getDates().size() > 0);
        assertTrue(weatherInfo.getMaxTemps().length > 0);
        assertTrue(weatherInfo.getMinTemps().length > 0);
    }

    // GeoLocationService should take a city name and returns a geo coordinates.
    @Test
    public void assertGeoLocationServiceReturnsCity() {
        GeoLocationService geoLocationService = new GeoLocationService();
        Location expectedLocation = new Location(24.466667, 39.6);
        assertEquals(expectedLocation, geoLocationService.search("Medina"));
    }

    // Test using the Adapter pattern
    @Test
    public void getWeatherInfo() {
        // Get the weather for Jeddah (21.543333, 39.172778)
        WeatherGeo weatherGeo = new OpenMeteoWeather();
        WeatherCity weatherAdapter = new WeatherAdapter(weatherGeo);
        WeatherInfo weatherInfo = weatherAdapter.getWeatherInfo("Jeddah");

        assertNotNull(weatherInfo);
        assertTrue(weatherInfo.getDates().size() > 0);
        assertTrue(weatherInfo.getMaxTemps().length > 0);
        assertTrue(weatherInfo.getMinTemps().length > 0);
    }
}


