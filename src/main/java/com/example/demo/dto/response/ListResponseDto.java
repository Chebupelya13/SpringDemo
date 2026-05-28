package com.example.demo.dto.response;

import java.util.List;

public class ListResponseDto<T> {

    public List<T> items;

    public int total;

    public ListResponseDto(List<T> items) {
        this.items = items;
        this.total = items.size();
    }

    public ListResponseDto() {}

}
