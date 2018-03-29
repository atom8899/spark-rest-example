package com.fake_company.spark_rest_example.model.rate;

import com.fake_company.spark_rest_example.model.rate.validation.DayValidation;
import com.fake_company.spark_rest_example.model.rate.validation.RateDayValidator;
import com.fake_company.spark_rest_example.model.rate.validation.ValidDayOfWeek;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Cacheable
@NamedQueries({
        @NamedQuery(name = "Rates.getAllRates",
                query = "SELECT r FROM Rate r"),
        @NamedQuery(name = "Rates.removeExistingRates",
                query = "DELETE FROM Rate r")
})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rate {


    private Integer id;
    @NotNull
    @NotBlank
    @Pattern(message = "must provide two times in the format HHMM-HHMM", regexp = "\\d\\d\\d\\d-\\d\\d\\d\\d")
    private String times;
    @NotNull
    private Integer price;
    @NotNull
    @NotBlank
    @ValidDayOfWeek(value = DayValidation.STRICT, message = "Must be a valid day of the week. Acceptable values: mon,tues,wed,thurs,fri,sat,sun")
    private String days;
    private List<String> validations;

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

    public Integer getPrice() {
        return price;
    }

    public String getTimes() {
        return times;
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

    public void setValidations(final List<String> validations) {
        this.validations = validations;
    }

    @Transient
    public List<String> getValidations() {
        return this.validations;
    }

}
