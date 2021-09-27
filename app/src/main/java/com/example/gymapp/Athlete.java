package com.example.gymapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Athlete {

    private int id;
    private String firstName;
    private String lastName;
    private String description;
    private int phone;


}
