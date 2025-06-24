package com.test.dto.CititesDto;

public class   CityCreateDto {

    private String CityName;
    private Long stateId;


    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }


}
