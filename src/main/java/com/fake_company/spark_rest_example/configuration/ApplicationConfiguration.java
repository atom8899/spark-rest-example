package com.fake_company.spark_rest_example.configuration;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

public class ApplicationConfiguration {
    private String version;
    private Integer port;
    private Connection connection;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Read ApplicationConfiguration file for application
     * @return
     */
    public static ApplicationConfiguration getConfiguration(final CommandLineArguments commandLineArguments) throws IOException {
        final ApplicationConfiguration config;
        Representer representer = new Representer();
        final Yaml yaml = new Yaml(representer);
        representer.getPropertyUtils().setSkipMissingProperties(true);
        try( InputStream in = Files.newInputStream( Paths.get( commandLineArguments.getConfigFileLocation()))){
            config = yaml.loadAs( in, ApplicationConfiguration.class);
            return config;
        } catch (IOException e) {
            throw e;
        }
    }
}
