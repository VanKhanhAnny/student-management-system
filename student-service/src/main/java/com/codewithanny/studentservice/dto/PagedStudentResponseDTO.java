package com.codewithanny.studentservice.dto;

import java.util.List;

public class PagedStudentResponseDTO {
    private List<StudentResponseDTO> students;
    private int page;
    private int size;
    private int totalPages;
    private int totalElements;

    public PagedStudentResponseDTO() {}

    public PagedStudentResponseDTO(
            List<StudentResponseDTO> students,
            int page,
            int size,
            int totalPages,
            int totalElements) {
        this.students = students;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<StudentResponseDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResponseDTO> students) {
        this.students = students;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
