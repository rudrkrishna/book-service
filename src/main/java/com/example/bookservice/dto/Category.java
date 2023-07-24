package com.example.bookservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {

    private Long id;

    private String categoryName;

    private String categoryDescription;


}
