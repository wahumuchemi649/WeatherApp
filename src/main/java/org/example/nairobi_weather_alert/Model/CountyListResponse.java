package org.example.nairobi_weather_alert.Model;

import java.util.List;
public class CountyListResponse {
    private List<String> counties;
    public CountyListResponse(List<String> counties) {
        this.counties = counties;
    }
    public List<String> getCounties() {
        return this.counties;
    }
    public void setCounties(List<String> counties) {
        this.counties = counties;
    }
}
