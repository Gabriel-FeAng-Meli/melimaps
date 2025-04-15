package io.meli.melimaps.domain.user;

import io.meli.melimaps.domain.vertex.Vertex;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NonNull;

@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NonNull
    private UserPreferences preferences;

    @NonNull
    private Vertex initialPosition;
}
