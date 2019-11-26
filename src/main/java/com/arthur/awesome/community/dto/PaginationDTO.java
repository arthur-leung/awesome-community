package com.arthur.awesome.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;

    private boolean showPrevious;
    private boolean showFirst;
    private boolean showNext;
    private boolean showLast;
    private int page;
    private int totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(int totalCount, int page, int pageSize) {

        int totalPage;
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        this.page = page;
        this.totalPage = totalPage;

        pages.add(page);
        for (int i = 1; i < 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = false;
        }

        if (pages.contains(1)) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        if (pages.contains(totalPage)) {
            showLast = false;
        } else {
            showLast = true;
        }
    }
}
