package com.borodinyar.java_monitoring;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    private final String configPath = "src/main/resources/config.properties";
    private String nodeUrl;
    private Map<String, String> oracles;

    Config() {
        try (InputStream input = new FileInputStream(configPath)) {
            Properties properties = new Properties();
            properties.load(input);

            nodeUrl = properties.getProperty("node_url");
            oracles = new HashMap<>();
            for (String token : properties.getProperty("tokens").split(", ")) {
                if (properties.getProperty(token) != null) {
                    oracles.put(token, properties.getProperty(token));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public Map<String, String> getOracles() {
        return oracles;
    }
}
