package com.datoramahw.pages;

import java.util.Comparator;
import java.util.Objects;

public class NavigationRoute implements Comparable{
    private long duration;
    private double distance;
    private String leaveAt;



    public NavigationRoute(String duration, String distance, String leaveAt) {
        this.duration = parseDuration(duration);
        this.distance = parseDistance(distance);
        this.leaveAt = leaveAt;
    }

    public long getDuration() {
        return duration;
    }


    public double getDistance() {
        return distance;
    }

    public String getLeaveAt() {
        return leaveAt;
    }

    @Override
    public int compareTo(Object o) {
        return Comparator.comparing(NavigationRoute::getDuration).
                thenComparing(NavigationRoute::getDistance).compare(this, (NavigationRoute) o);
    }


    private enum DistanceUnits {
        km{
            @Override
            protected double calculateKm(double amount) {
                return amount;
            }
        },
        meters{
            @Override
            protected double calculateKm(double amount) {
                return amount/1000;
            }
        };
    protected abstract double calculateKm(double amount);
    }
    private enum TimeUnits {
        min{
            @Override
            protected long calculateMins(int amount) {
                return amount;
            }
        },
        hours {
            @Override
            protected long calculateMins(int amount) {
                return amount * 60;
            }
        },
        hour{
            @Override
            protected long calculateMins(int amount) {
                return hours.calculateMins(amount);
            }
        },
        days{
            @Override
            protected long calculateMins(int amount) {
                return hours.calculateMins(amount) * 24;
            }
        },
        day{
            @Override
            protected long calculateMins(int amount) {
                return days.calculateMins(amount);
            }
        };
    protected abstract long calculateMins(int amount);
    }

    private Long parseDuration(String duration) {
        int amount = Integer.valueOf(duration.split(" ")[0]);
        String units = duration.split(" ")[1];
        return TimeUnits.valueOf(units).calculateMins(amount);
    }

    private Double parseDistance(String distance) {
        double amount = Double.valueOf(distance.split(" ")[0]);
        String units = distance.split(" ")[1];
        return DistanceUnits.valueOf(units).calculateKm(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NavigationRoute that = (NavigationRoute) o;
        return Objects.equals(duration, that.duration) &&
                Objects.equals(distance, that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, distance);
    }

    @Override
    public String toString() {
        return " time: " + this.getDuration()
                + "min lenght: " + this.getDistance()
                + " km leave at: " + this.getLeaveAt();
    }
}
