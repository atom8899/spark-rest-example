package com.fake_company.spark_rest_example.model;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rates.getAllRates",
                query = "SELECT r FROM Rate r")
})
public class Rate {


    private Integer id;
    /**
     * I'm sorry but I can't stand the fact that a "list" is being passed to me as a String. It's not proper JSON
     */
    private String times;
    private Integer price;
    /**
     * I'm sorry but I can't stand the fact that a "list" is being passed to me as a String. It's not proper JSON
     */
    private String days;

    public Rate(final String days, final String times, final Integer price) {
        this.days = days;
        this.times = times;
        this.price = price;
    }

    public Rate() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(final String days) {
        this.days = days;
    }

    public void setTimes(final String times) {
        this.times = times;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }


    public String getTimes() {
        return times;
    }


}
