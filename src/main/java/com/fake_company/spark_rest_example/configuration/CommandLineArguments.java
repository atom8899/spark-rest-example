package com.fake_company.spark_rest_example.configuration;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.IOException;

public class CommandLineArguments {

    @Parameter(names = {"-p", "-port"}, description = "Server Port", required = false)
    private Integer port = 9000;

    public Integer getPort() {
        return port;
    }

    public static CommandLineArguments parse(final String[] args) {
        final CommandLineArguments commandLineArguments = new CommandLineArguments();
        final JCommander jCommander = JCommander.newBuilder()
                .addObject(commandLineArguments)
                .build();
        try {
            jCommander.parse(args);
            return commandLineArguments;
        } catch (ParameterException pe) {
            jCommander.usage();
            throw pe;
        }
    }
}
