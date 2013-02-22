package fr.ethilvan.bukkit.travels;

public class TimeUtils {

    private TimeUtils() {
    }
    
    public static int checkDelaySecToTicks(int seconds) {
        return seconds * 20;
    }
    
    public static int ticksToCheckDelaySec(int ticks) {
        return ticks / 20;
    }
    
    public static int travelMinutesToTicks(int minutes) {
        return (int) (((minutes + 960) % 1440) * 60 / 3.6);
    }
    
    public static int ticksToTravelMinutes(int ticks) {
        return (int) (Math.round(ticks * 3.6 / 60) + 480) % 1440;
    }
}