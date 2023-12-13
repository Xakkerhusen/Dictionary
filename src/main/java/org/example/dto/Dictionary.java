package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Dictionary {
    private Integer id;
    private String wordEng;
    private String wordUzb;
    private String description;
}
