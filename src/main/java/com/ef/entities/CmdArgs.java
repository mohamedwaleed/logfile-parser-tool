package com.ef.entities;

/**
 * Created by mohamed on 14/10/17.
 */
public class CmdArgs {

    private String startDate;

    private Duration duration;

    private Integer threshold;

    private String accessLogPath;

    public void setAccessLogPath(String accessLogPath) {
        this.accessLogPath = accessLogPath;
    }

    public String getAccessLogPath() {
        return accessLogPath;
    }

    public enum Duration {
        DAILY("daily"),HOURLY("hourly");
        private String duration;
        Duration(String duration){
            this.duration = duration;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
