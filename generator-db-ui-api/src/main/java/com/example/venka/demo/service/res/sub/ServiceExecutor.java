package com.example.venka.demo.service.res.sub;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface ServiceExecutor {

    void execute(final File directory, final Map<String, Object> body) throws IOException;
}
