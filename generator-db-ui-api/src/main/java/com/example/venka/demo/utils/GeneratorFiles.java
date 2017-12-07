package com.example.venka.demo.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public class GeneratorFiles {

    public File get(final Map<String, String> body) {
        final File directory = new File("generator-db-ui-template");
        setMetadata(directory, body);

        // change templates
        // create files
        return directory;
    }

    private void setMetadata(final File directory, final Map<String, String> body) {
        final String groupId = body.get("groupId");
        final String artifactId = body.get("artifactId");
        final String name = body.get("name");
        final String packageName = body.get("packageName");
        final String javaVersion = body.get("javaVersion");
    }

    public String getName(final Map<String, String> body) {
        return body.get("name");
    }
}
