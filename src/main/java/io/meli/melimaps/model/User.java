package io.meli.melimaps.model;

import io.meli.melimaps.enums.EnumTransport;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NonNull;

@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @NonNull
    private EnumTransport prefferedTransport;

    @NonNull
    private Vertex initialPosition;

    private boolean considerAccessibility = false;
    private boolean considerCO2Emissions = false;
}
