package com.klaus.consumer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {

    @JsonIgnore
    private String _id;

    private int mid;

    private String name;

    private String descri;

    private String timelong;

    private String issue;

    private String shoot;

    private Double score;

    private String language;

    private String genres;

    private String actors;

    private String directors;



}