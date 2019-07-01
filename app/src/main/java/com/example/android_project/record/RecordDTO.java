package com.example.android_project.record;

public class RecordDTO {
    private String repair_name, repair_cost, memo, mileage, repair_term, record_date;
    private String oil_cost, car_mileage, station, oil,record_id;

    public RecordDTO(String repair_name, String repair_cost, String memo, String mileage, String repair_term, String record_date,String record_id) {
        this.repair_name = repair_name;
        this.repair_cost = repair_cost;
        this.memo = memo;
        this.mileage = mileage;
        this.repair_term = repair_term;
        this.record_date = record_date;
        this.record_id = record_id;
    }

    public RecordDTO(String station, String oil_cost, String car_mileage, String record_date, String oil, String record_id) {
        this.station = station;
        this.record_date = record_date;
        this.oil_cost = oil_cost;
        this.car_mileage = car_mileage;
        this.oil = oil;
        this.record_id = record_id;
    }


    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getCar_mileage() {
        return car_mileage;
    }

    public void setCar_mileage(String car_mileage) {
        this.car_mileage = car_mileage;
    }

    public String getOil_cost() {
        return oil_cost;
    }

    public void setOil_cost(String oil_cost) {
        this.oil_cost = oil_cost;
    }

    public String getRepair_name() {
        return repair_name;
    }

    public void setRepair_name(String repair_name) {
        this.repair_name = repair_name;
    }

    public String getRepair_cost() {
        return repair_cost;
    }

    public void setRepair_cost(String repair_cost) {
        this.repair_cost = repair_cost;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getRepair_term() {
        return repair_term;
    }

    public void setRepair_term(String repair_term) {
        this.repair_term = repair_term;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }
}
