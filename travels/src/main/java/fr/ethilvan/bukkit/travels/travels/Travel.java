package fr.ethilvan.bukkit.travels.travels;

public class Travel {

    private String name;
    private String departure;
    private String destination;
    private int schedule;
    private double price;
    private boolean active;

    public Travel(String name) {
        this.name = name;
        this.departure = null;
        this.destination = null;
        this.schedule = -1;
        this.price = 0;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(Port port) {
        this.departure = port.getName();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(Port port) {
        this.destination = port.getName();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean hasSchedule() {
        return schedule != -1;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
