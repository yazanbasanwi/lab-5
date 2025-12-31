package sa.edu.kau.fcit.cpit252.weather;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeatherInfo {
    private double latitude;
    private double longitude;
    private String city;
    private ArrayList<LocalDate> dates = new ArrayList<>();
    private int[] maxTemps;
    private int[] minTemps;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<LocalDate> getDates() {
        return dates;
    }

    public void setDates(ArrayList<LocalDate> dates) {
        this.dates = dates;
    }

    public int[] getMaxTemps() {
        return maxTemps;
    }

    public void setMaxTemps(int[] maxTemps) {
        this.maxTemps = maxTemps;
    }

    public int[] getMinTemps() {
        return minTemps;
    }

    public void setMinTemps(int[] minTemps) {
        this.minTemps = minTemps;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("\"WeatherInfo { ")
                .append("\"city\": " + this.city +
                        ", \"latitude\": " + this.latitude + ", \"longitude\": " + this.longitude)
                .append(", \"Daily Forecasts\": [\n");
        for (int i = 0; i < this.dates.size(); i++) {
            sb.append(" {\"Date\": \"" + this.dates.get(i).toString() + "\"")
                    .append(", \"MaxTemperature\": " + this.maxTemps[i])
                    .append(", \"MinTemperature\": " + this.minTemps[i])
                    .append("},\n");
        }
        return sb.append("]}\n").toString();
    }
}
