package com.example.android_project.home_alignlist;

public class AlignDTO {

    private String repair_id,repair_name,repair_term,repair_mile;

    public AlignDTO(String repair_id, String repair_name, String repair_term, String repair_mile) {
        this.repair_id = repair_id;
        this.repair_name = repair_name;
        this.repair_term = repair_term;
        this.repair_mile = repair_mile;
    }

    public String getRepair_id() {
        return repair_id;
    }

    public void setRepair_id(String repair_id) {
        this.repair_id = repair_id;
    }

    public String getRepair_name() {
        return repair_name;
    }

    public void setRepair_name(String repair_name) {
        this.repair_name = repair_name;
    }

    public String getRepair_term() {
        return repair_term;
    }

    public void setRepair_term(String repair_term) {
        this.repair_term = repair_term;
    }

    public String getRepair_mile() {
        return repair_mile;
    }

    public void setRepair_mile(String repair_mile) {
        this.repair_mile = repair_mile;
    }
}
