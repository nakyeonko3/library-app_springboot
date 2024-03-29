package com.group.libraryapp.domain;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    public Book(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어 왔습니다.",name));
        }
        this.name = name;
    }

    protected Book(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
