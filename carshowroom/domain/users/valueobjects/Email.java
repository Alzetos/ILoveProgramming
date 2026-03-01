package ru.chekhet.carshowroom.domain.users.valueobjects;

import ru.chekhet.carshowroom.domain.exceptions.DomainValidationException;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public Email {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new DomainValidationException("Invalid email format: " + value);
        }
    }
}
