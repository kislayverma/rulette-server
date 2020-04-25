package com.github.kislayverma.rulette.rest.utils;

import com.github.kislayverma.rulette.rest.model.PaginatedResult;

import java.util.List;

public class PaginationUtil {
    /**
     * This method returns sublists of the given list in a paginated way
     * @param data The complete subparts of which are to be returned as pages
     * @param pageNum The number of the page to be returned
     * @param pageSize The number of records in each page
     * @param <T> The data type of the input list
     * @return A paginated result build as per input page size and page number
     */
    public static <T> PaginatedResult<T> getPaginatedData(List<T> data, int pageNum, int pageSize) {
        int startingIndex = (pageNum - 1) * pageSize;
        if (startingIndex >= data.size() || startingIndex < 0) {
            return new PaginatedResult<>(null, pageNum, pageSize, data.size(), false, true);
        } else {
            boolean hasNext = (startingIndex + pageSize) < data.size() - 1;
            boolean hasPrev  = startingIndex > 0;
            int toIndex = Math.min((startingIndex + 1) * pageSize, data.size() - 1);
            if (toIndex == startingIndex) {
                toIndex++;
            }
            return new PaginatedResult<>(
                data.subList(startingIndex, toIndex),
                pageNum,
                pageSize,
                data.size(),
                hasNext,
                hasPrev
            );
        }
    }
}
