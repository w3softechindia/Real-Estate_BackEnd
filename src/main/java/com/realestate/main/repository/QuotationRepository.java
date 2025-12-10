package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.realestate.main.entity.Quotation;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Integer>{

}
