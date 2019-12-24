package com.github.jasondavies1.applicationpropertiesresolver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Properties {
    private List<Property> properties = new ArrayList<>();
}
