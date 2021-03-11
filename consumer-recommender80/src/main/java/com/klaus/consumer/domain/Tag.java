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
public class Tag {

    @JsonIgnore
    private String _id;

    private int uid;

    private int mid;

    private String tag;

    private long timestamp;
}