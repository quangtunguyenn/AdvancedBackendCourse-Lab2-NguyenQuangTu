package com.devnguyen.Lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Lecturers")
@DiscriminatorValue("LECTURER")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lecturer extends User {

    @Column(name = "lecturer_code", nullable = false, length = 20, unique = true)
    private String lecturerCode;

    @Column(name = "department", nullable = false, length = 100)
    private String department;
}