package com.realestate.main.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String type;

    public String title;

    public String message;

    public String audience;

    public String priority;

    public LocalDate startDate;

    public LocalDate endDate;

    public String department;

    public String postedBy;

    public String attachmentPath;
    
    @ManyToOne
    private Agency agency;


    
}
