package com.project.bookstorage.dtos;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
    private List<UserDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
