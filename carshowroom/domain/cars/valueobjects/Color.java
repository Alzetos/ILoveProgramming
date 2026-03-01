package ru.chekhet.carshowroom.domain.cars.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

import java.util.regex.Pattern;

public record Color(String hexCode) {
    private static final Pattern HEX_PATTERN =
            Pattern.compile("^#([A-Fa-f0-9]{6})$");

    public Color {
        if (hexCode == null || !HEX_PATTERN.matcher(hexCode).matches()) {
            throw new DomainValidationException("Invalid hex color format: " + hexCode + ". Format #FFFFFF");
        }
    }
}
