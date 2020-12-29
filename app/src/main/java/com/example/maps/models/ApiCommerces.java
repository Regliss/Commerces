package com.example.maps.models;

import java.util.List;

public class ApiCommerces {
    private int nhits;

    public List<ApiRecords> getRecords() {
        return records;
    }

    public void setRecords(List<ApiRecords> records) {
        this.records = records;
    }

    private List<ApiRecords> records;

    public int getNhits() {
        return nhits;
    }

    public void setNhits(int nhits) {
        this.nhits = nhits;
    }

}
