package com.fake_company.spark_rest_example.model.transformers;
import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonResponseTransformer implements ResponseTransformer{

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }

}
