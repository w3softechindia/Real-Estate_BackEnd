package com.realestate.main.dto;

import java.time.LocalDate;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

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
    
    
    private Agency agency;

    
    private Admin admin;
}
