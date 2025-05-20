package com.group4.kafka.model;

public class TestSensorData {
    public static void main(String[] args) {
        SensorData data = new SensorData(
                "TEMP001",
                "2025-05-20T15:00:00Z",
                23.7,
                "HEL"
        );

        System.out.println("Objet SensorData :");
        System.out.println(data);
    }
}
