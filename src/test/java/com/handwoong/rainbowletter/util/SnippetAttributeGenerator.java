package com.handwoong.rainbowletter.util;

import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.snippet.Attributes.Attribute;

public class SnippetAttributeGenerator {
    public static Attribute constraints(final String value) {
        return key("constraints").value(value);
    }

    private SnippetAttributeGenerator() {
    }
}
