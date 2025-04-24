package com.realestate.main.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserMapper userMapper= Mappers.getMapper(UserMapper.class);
	
	AdminDto toAdminDto(Admin admin);
	
	AgencyDto toAgencyDto(Agency agency);
	
	AgentDto toAgentDto(Agent agent);
	
	CustomerDto toCustomerDto(Customer customer);
}
