package com.example.weatherapp.api;

public class BODY {
    private String dataType;
    private ITEMS items;
    private int totalCount;

    public BODY(String dataType, ITEMS items, int totalCount) {
        this.dataType = dataType;
        this.items = items;
        this.totalCount = totalCount;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public ITEMS getItems() {
        return items;
    }

    public void setItems(ITEMS items) {
        this.items = items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
