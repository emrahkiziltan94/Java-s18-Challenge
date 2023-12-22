package com.workintech.s18challenge.dto;

public record BookResponse(long id,String name,String categoryName,AuthorResponse authorResponse) {
}
