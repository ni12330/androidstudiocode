package com.example.android_project.Katec;

public class Oil_DTO {

    private String oil_name,oil_price,distance,gis_x,gis_y,oil_comp,comp_code;

    public Oil_DTO(String oil_name, String oil_price, String distance, String gis_x, String gis_y,String oil_comp,String comp_code) {
        this.oil_name = oil_name;
        this.oil_price = oil_price;
        this.distance = distance;
        this.gis_x = gis_x;
        this.gis_y = gis_y;
        this.oil_comp = oil_comp;
        this.comp_code = comp_code;
    }


    public Oil_DTO(String oil_name, String oil_price, String distance, String gis_x, String gis_y,String oil_comp) {
        this.oil_name = oil_name;
        this.oil_price = oil_price;
        this.distance = distance;
        this.gis_x = gis_x;
        this.gis_y = gis_y;
        this.oil_comp = oil_comp;
    }


    public String getComp_code() {
        return comp_code;
    }

    public void setComp_code(String comp_code) {
        this.comp_code = comp_code;
    }

    public String getOil_comp() {
        return oil_comp;
    }

    public void setOil_comp(String oil_comp) {
        this.oil_comp = oil_comp;
    }

    public String getOil_name() {
        return oil_name;
    }

    public void setOil_name(String oil_name) {
        this.oil_name = oil_name;
    }

    public String getOil_price() {
        return oil_price;
    }

    public void setOil_price(String oil_price) {
        this.oil_price = oil_price;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getGis_x() {
        return gis_x;
    }

    public void setGis_x(String gis_x) {
        this.gis_x = gis_x;
    }

    public String getGis_y() {
        return gis_y;
    }

    public void setGis_y(String gis_y) {
        this.gis_y = gis_y;
    }
}
