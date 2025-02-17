package org.ayple.hcfcore.helpers;

public class DateTimeUtils {

    public static String formatSecondsToHoursMinutes(int totalSeconds) {
        int hours = totalSeconds / 3600; // 1 hour = 3600 seconds
        int remainingSeconds = totalSeconds % 3600;
        int minutes = remainingSeconds / 60; // 1 minute = 60 seconds
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String formatSecondsToHoursMinutesSeconds(int totalSeconds) {
        int hours = totalSeconds / 3600; // 1 hour = 3600 seconds
        int remainingSeconds = totalSeconds % 3600;
        int minutes = remainingSeconds / 60; // 1 minute = 60 seconds
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public static String formatSecondsToMinutesSeconds(int totalSeconds) {
//        int hours = totalSeconds / 3600; // 1 hour = 3600 seconds
        int remainingSeconds = totalSeconds % 3600;
        int minutes = remainingSeconds / 60; // 1 minute = 60 seconds
        int seconds = remainingSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
