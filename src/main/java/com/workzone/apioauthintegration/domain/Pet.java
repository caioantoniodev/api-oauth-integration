package com.workzone.apioauthintegration.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Pet {
    public int id;
    public String name;
    public Category category;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;
}

