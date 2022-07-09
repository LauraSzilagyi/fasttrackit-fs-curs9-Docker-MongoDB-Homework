package ro.fasttrackit.curs9.homework.model;

import ro.fasttrackit.curs9.homework.entity.CleaningProcedure;

import java.time.LocalDate;

public record CleanUpModel(LocalDate date,
                           String procedure,
                           CleaningProcedure cleaningProcedure) {
}
