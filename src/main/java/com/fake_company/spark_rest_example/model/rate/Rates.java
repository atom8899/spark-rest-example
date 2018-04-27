package com.fake_company.spark_rest_example.model.rate;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Rates implements Iterable<Rate> {

    @JacksonXmlElementWrapper(localName = "rates")
    @JacksonXmlProperty(localName = "rate")
    private List<Rate> rates;

    public Rates(final List<Rate> rates) {
        this.rates = rates;
    }

    public Rates() { }

    public List<Rate> getRates() {
        return rates;
    }

    @Override
    public Iterator<Rate> iterator() {
        return rates.iterator();
    }

    @Override
    public void forEach(Consumer<? super Rate> action) {
        rates.forEach(action);
    }

    @Override
    public Spliterator<Rate> spliterator() {
        return rates.spliterator();
    }
}
