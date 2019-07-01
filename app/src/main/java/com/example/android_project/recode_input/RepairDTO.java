package com.example.android_project.recode_input;

public class RepairDTO {
    private String  repair_id, repair_name, repair_term, repair_mile, user_id;
    private boolean confirm;
    private String checkbox_val;
    private String cost, memo;
    private int num;

    public RepairDTO(){};

    public RepairDTO(String repair_id, String repair_name, String repair_term, String repair_mile, boolean confirm) {
        this.repair_id = repair_id;
        this.repair_name = repair_name;
        this.repair_term = repair_term;
        this.repair_mile = repair_mile;
        this.confirm = confirm;
    }

    public RepairDTO(String repair_name, String cost, String memo){
        this.repair_name = repair_name;
        this.cost = cost;
        this.memo = memo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCheckbox_val() {
        return checkbox_val;
    }

    public void setCheckbox_val(String checkbox_val) {
        this.checkbox_val = checkbox_val;
    }

    public RepairDTO(String repair_name) {
        this.repair_name = repair_name;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getRepair_name() {
        return repair_name;
    }

    public void setRepair_name(String repair_name) {
        this.repair_name = repair_name;
    }

    public String getRepair_id() {
        return repair_id;
    }

    public void setRepair_id(String repair_id) {
        this.repair_id = repair_id;
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
