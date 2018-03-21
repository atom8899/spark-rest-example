package com.fake_company.spark_rest_example.configuration;

public class Connection {
    private String url;
    private Integer port;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String dbUrl) {
        this.url = dbUrl;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer dbPort) {
        this.port = dbPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
