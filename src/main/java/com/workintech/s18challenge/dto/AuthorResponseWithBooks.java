package com.workintech.s18challenge.dto;

import java.util.List;

public record AuthorResponseWithBooks(List<BookResponse> responseList) {
}
