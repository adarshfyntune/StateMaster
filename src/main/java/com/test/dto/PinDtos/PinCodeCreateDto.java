package com.test.dto.PinDtos;

public class PinCodeCreateDto {

    private Long pincode;
    private Long cityId;

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
