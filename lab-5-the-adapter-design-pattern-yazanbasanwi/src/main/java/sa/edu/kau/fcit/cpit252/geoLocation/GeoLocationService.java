package sa.edu.kau.fcit.cpit252.geoLocation;

import java.util.HashMap;
import java.util.Map;

public class GeoLocationService {

    private final Map<String, Location> GEO_LOCATIONS = new HashMap<String, Location>();

    public GeoLocationService() {
        GEO_LOCATIONS.put("Makkah", new Location(21.383333, 39.850000));
        GEO_LOCATIONS.put("Medina", new Location(24.466667, 39.6));
        GEO_LOCATIONS.put("Jeddah", new Location(21.543333, 39.172778));
        GEO_LOCATIONS.put("Riyadh", new Location(24.633333, 46.716667));
        GEO_LOCATIONS.put("Tabuk", new Location(28.397222, 36.578889));
        GEO_LOCATIONS.put("Khobar", new Location(26.283333, 50.2));
        GEO_LOCATIONS.put("Abha", new Location(18.216944, 42.505278));
        GEO_LOCATIONS.put("Jazan", new Location(16.889167, 42.561111));
    }

    public Location search(String cityName){
        return GEO_LOCATIONS.get(cityName);
    }
}
