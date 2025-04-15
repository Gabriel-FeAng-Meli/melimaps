package io.meli.melimaps.domain.user;

import io.meli.melimaps.enums.EnumTransport;

public record UserPreferences(EnumTransport transportPreference, Boolean ecoFriendly, Boolean considerAccessibility) {}
