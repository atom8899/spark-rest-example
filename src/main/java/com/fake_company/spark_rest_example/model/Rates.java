package com.fake_company.spark_rest_example.model;

import java.time.ZonedDateTime;

public class Rates {

    /**
     * I'm sorry but I can't stand the fact that a "list" is being passed to me as a String. It's not proper JSON
     */
    private final String days;
    /**
     * I'm sorry but I can't stand the fact that a "list" is being passed to me as a String. It's not proper JSON
     */
    private final String times;
    private final Integer price;

    public Rates(final String days, final String times, final Integer price) {
        this.days = days;
        this.times = times;
        this.price = price;
    }

    public String getDays() {
        return days;
    }

    public String getTimes() {
        return times;
    }

    public Integer getPrice() {
        return price;
    }


}
