package com.fake_company.spark_rest_example.model.transformers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import spark.ResponseTransformer;

public class XmlResponseTransformer implements ResponseTransformer {
    final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public String render(Object model) throws Exception {
        return xmlMapper.writeValueAsString(model);
    }
}
