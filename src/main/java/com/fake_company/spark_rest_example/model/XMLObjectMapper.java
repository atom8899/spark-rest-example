package com.fake_company.spark_rest_example.model;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLObjectMapper extends XmlMapper {

    public XMLObjectMapper() {
        super(new JacksonXmlModule());
    }
}
