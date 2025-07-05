//package com.test.dto.PinDtos;
//
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDate;
//
//public class PinCodeSearchDto {
//
//    private  Long pincode;
//
//    private  String cityName;
//
//    private String stateName;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    private LocalDate createdAtFrom;
//
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    private LocalDate createdAtTo;
//
//
//    public Long getPincode() {
//        return pincode;
//    }
//
//    public void setPincode(Long pincode) {
//        this.pincode = pincode;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getStateName() {
//        return stateName;
//    }
//
//    public void setStateName(String stateName) {
//        this.stateName = stateName;
//    }
//
//    public LocalDate getCreatedAtFrom() {
//        return createdAtFrom;
//    }
//
//    public void setCreatedAtFrom(LocalDate createdAtFrom) {
//        this.createdAtFrom = createdAtFrom;
//    }
//
//    public LocalDate getCreatedAtTo() {
//        return createdAtTo;
//    }
//
//    public void setCreatedAtTo(LocalDate createdAtTo) {
//        this.createdAtTo = createdAtTo;
//    }
//
//    public PinCodeSearchDto(Long pincode, String cityName, String stateName, LocalDate createdAtFrom, LocalDate createdAtTo) {
//        this.pincode = pincode;
//        this.cityName = cityName;
//        this.stateName = stateName;
//        this.createdAtFrom = createdAtFrom;
//        this.createdAtTo = createdAtTo;
//
//    }
//
//    public PinCodeSearchDto(Long pincode, String cityName, String stateName) {
//        this.pincode = pincode;
//        this.cityName = cityName;
//        this.stateName = stateName;
//    }
//
//    public PinCodeSearchDto() {
//    }
//}