package org.example.contoller;

public enum MageControllerCode {
    NOT_FOUND,
    DONE,
    BAD_REQUEST,
    BAD_ARGUMENT;

    @Override
    public String toString() {
        return name().toLowerCase(); // Customize the string representation if needed
    }
}
