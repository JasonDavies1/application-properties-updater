package com.github.jasondavies1.applicationpropertiesresolver.domain;

import lombok.Data;

@Data
public class Property {
    private String key;
    private String value;
    private boolean include;
}
