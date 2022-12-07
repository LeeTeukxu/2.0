package com.zhide.dtsystem.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

public class PageableUtils {
    public static Pageable create(int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction order = Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(order, sortField);
        org.springframework.data.domain.Pageable p = PageRequest.of(pageIndex, pageSize, sort);
        return p;
    }
    public static Pageable create(HttpServletRequest request){
        String sortOrder=request.getParameter("sortOrder");
        String sortField=request.getParameter("sortField");
        Integer pageSize=Integer.parseInt(request.getParameter("pageSize"));
        Integer pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
        Sort.Direction order = Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(order, sortField);
        org.springframework.data.domain.Pageable p = PageRequest.of(pageIndex, pageSize, sort);
        return p;
    }
}
