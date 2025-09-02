package com.realestate.main.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgencyVentureDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.LeadDto;
import com.realestate.main.dto.PlotsDto;
import com.realestate.main.dto.ReviewDto;
import com.realestate.main.dto.TokenDto;
import com.realestate.main.dto.VentureDto;
import com.realestate.main.dto.VisitDto;
import com.realestate.main.entity.Admin; 
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.AgencyVenture;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Lead;
import com.realestate.main.entity.Plots;
import com.realestate.main.entity.Reviews;
import com.realestate.main.entity.Token;
import com.realestate.main.entity.Venture;
import com.realestate.main.entity.Visit;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserMapper userMapper= Mappers.getMapper(UserMapper.class);
	
	AdminDto toAdminDto(Admin admin);
	
	AgencyDto toAgencyDto(Agency agency);
	
	AgentDto toAgentDto(Agent agent);
	
	CustomerDto toCustomerDto(Customer customer);
	
	VentureDto toVentureDto(Venture venture);
	
	PlotsDto toPLotsDto(Plots plots);
	
	LeadDto toLeadDto(Lead lead);
	
 
	VisitDto toVisitDto(Visit visit);
	
	AgencyVentureDto toAgencyVentureDto(AgencyVenture agencyVenture);

	TokenDto toTokenDto(Token token);

	
	
	ReviewDto toReviewDto(Reviews reviews);
	
	List<ReviewDto>  toReviews(List<Reviews> reviews);


}
