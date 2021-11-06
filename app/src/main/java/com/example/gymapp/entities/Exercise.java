package com.example.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    private int id;
    private String exName;
    private int repeats;
    private int sets;
    private Workout workout;
}
