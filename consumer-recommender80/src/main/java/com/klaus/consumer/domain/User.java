package com.klaus.consumer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {


    @JsonIgnore
    private String _id;

    private int uid;

    private String username;

    private String password;

    private boolean first;

    private long timestamp;

    private List<String> prefGenres = new ArrayList<>();

}