package com.test.config;

public class PinCodeSearchDto {

    private String stateName;
    private String cityName;
    private Long pinCode;

    public PinCodeSearchDto(String stateName, String cityName, Long pinCode) {
        this.stateName = stateName;
        this.cityName = cityName;
        this.pinCode = pinCode;
    }

    public PinCodeSearchDto() {
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }
}
