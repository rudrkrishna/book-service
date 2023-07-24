package com.example.bookservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long id;

    private String categoryName;

    private String categoryDescription;


}
