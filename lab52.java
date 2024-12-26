import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class lab52 {
    public static void main(String[] args) {
        CompletableFuture<WeatherData> city1Weather = CompletableFuture.supplyAsync(() -> fetchWeather("City1"));
        CompletableFuture<WeatherData> city2Weather = CompletableFuture.supplyAsync(() -> fetchWeather("City2"));
        CompletableFuture<WeatherData> city3Weather = CompletableFuture.supplyAsync(() -> fetchWeather("City3"));
        CompletableFuture<Void> allWeatherData = CompletableFuture.allOf(city1Weather, city2Weather, city3Weather);
        try {
            allWeatherData.join();
            WeatherData city1 = city1Weather.get();
            WeatherData city2 = city2Weather.get();
            WeatherData city3 = city3Weather.get();
            System.out.println("Analyzing weather data...");
            analyzeWeather(city1, city2, city3);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static WeatherData fetchWeather(String city) {
        try {
            System.out.println("Fetching weather for " + city);
            TimeUnit.SECONDS.sleep(2); // Simulating delay
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    // Simulated data
        return new WeatherData(city, (int) (Math.random() * 40), (int) (Math.random() * 100), (int) (Math.random() * 15));
    }

    private static void analyzeWeather(WeatherData... cities) {
        for (WeatherData city : cities) {
            System.out.println(city);
            if (city.temperature > 25 && city.humidity < 70 && city.windSpeed < 10) {
                System.out.println(city.cityName + " is great for the beach!\n");
            } else if (city.temperature < 10) {
                System.out.println(city.cityName + " is cold, wear warm clothes!\n");
            } else {
                System.out.println(city.cityName + " has moderate weather.\n");
            }
        }
    }
    //weather details
    static class WeatherData {
        String cityName;
        int temperature;
        int humidity;
        int windSpeed;

        public WeatherData(String cityName, int temperature, int humidity, int windSpeed) {
            this.cityName = cityName;
            this.temperature = temperature;
            this.humidity = humidity;
            this.windSpeed = windSpeed;
        }
        @Override
        public String toString() {
            return "WeatherData{" +
                    "cityName='" + cityName + '\'' +
                    ", temperature=" + temperature +
                    ", humidity=" + humidity +
                    ", windSpeed=" + windSpeed +
                    '}';
        }
    }
}
