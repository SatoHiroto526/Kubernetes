package com.example.servie;

import jakarta.enterprise.context.RequestScoped;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@RequestScoped
public class Service {

    private static final String ENV = "ENV";

    private static final String FILE_PATH = "/opt/jboss/wildfly/secret/secret.properties";

    private static final String PASSWORD_KEY = "password";

    public String getConfigMapEnv() {
        return System.getenv(ENV);
    }
    
    public String secretMountData() {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Path.of(FILE_PATH))) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return properties.getProperty(PASSWORD_KEY);
    }
}
