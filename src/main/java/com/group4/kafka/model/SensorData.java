package com.group4.kafka.model;

public class SensorData {
    private String sensorId;
    private String timestamp;
    private double value;
    private String unit;

    public SensorData() {}

    public SensorData(String sensorId, String timestamp, double value, String unit) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.value = value;
        this.unit = unit;
    }

    // Getters & Setters
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    @Override
    public String toString() {
        return "SensorData{" +
                "sensorId='" + sensorId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}
