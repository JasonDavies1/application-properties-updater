package com.github.jasondavies1.applicationpropertiesresolver.service;

import com.github.jasondavies1.applicationpropertiesresolver.domain.Properties;
import com.github.jasondavies1.applicationpropertiesresolver.domain.Property;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MultipartFileToPropertiesConverter implements Converter<MultipartFile, Properties> {

    @Override
    public Properties convert(final MultipartFile multipartFile) {
        try {
            final String fileContents = new String(multipartFile.getBytes());
            final String[] lines = fileContents.split("\n");
            return new Properties(Stream.of(lines)
                    .filter(s -> !s.isBlank())
                    .map(removeCarriageReturn())
                    .map(toProperty())
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read bytes from uploaded file");
        }

    }

    private Function<String, Property> toProperty() {
        return s -> {
            final Property property = new Property();
            final String[] components = s.split("=", 2);
            property.setKey(components[0]);
            property.setValue(components[1]);
            property.setInclude(true);
            return property;
        };
    }

    private Function<String, String> removeCarriageReturn() {
        return s -> s.replaceAll("\r", "");
    }
}
