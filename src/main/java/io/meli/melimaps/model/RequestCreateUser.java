package io.meli.melimaps.model;

public record RequestCreateUser(
    String name, String transport, Boolean hurry, Boolean accessibility, Boolean ecologic, Boolean budget
) {

}
