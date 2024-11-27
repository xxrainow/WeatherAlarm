package com.example.weatherapp.api;

import java.util.List;

public class ITEMS {
    private List<ITEM> item;

    public ITEMS(List<ITEM> item) {
        this.item = item;
    }

    public List<ITEM> getItem() {
        return item;
    }

    public void setItem(List<ITEM> item) {
        this.item = item;
    }
}
