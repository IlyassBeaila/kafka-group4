package com.group4.kafka.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    public String name; // nom de la ville
    public Main main;
    public Wind wind;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        public double temp;
        public int humidity;
        // Méthode pour convertir Kelvin -> Celsius
        public double getTempCelsius() {
            return Math.round(temp - 273.15);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        public double speed;
    }

    @Override
    public String toString() {
        return "Ville: " + name + ", Température: " + main.getTempCelsius() + "C, Humidité: " + main.humidity + "%, Vent: " + wind.speed + " m/s";
    }
}