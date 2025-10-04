package com.dp.vstore_productservice.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SortingHelper {

    public static List<Sort.Order> sortHelper(List<String> sortBy) {
        return sortBy.stream()
                .map(s -> {
                    String[] parts = s.split(",");
                    String field = parts[0];
                    Sort.Direction direction = parts.length > 1 && parts[1].equals("asc") ?
                            Sort.Direction.ASC : Sort.Direction.DESC;
                    return new Sort.Order(direction, field);
                })
                .toList();
    }
}
