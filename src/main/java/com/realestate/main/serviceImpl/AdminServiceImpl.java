package com.realestate.main.serviceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.PlotsDto;
import com.realestate.main.dto.VentureDto;
import com.realestate.main.emailConfiguration.EmailUtil;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Plots;
import com.realestate.main.entity.RealEStateUser;
import com.realestate.main.entity.Role;
import com.realestate.main.entity.Venture;
import com.realestate.main.exceptions.PropertyNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.mapper.UserMapper;

import com.realestate.main.repository.AdminRepository;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.PlotsRepository;
import com.realestate.main.repository.RealEstateUserRepo;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.repository.VentureRepository;
import com.realestate.main.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private RealEstateUserRepo userRepo;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private VentureRepository ventureRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private PlotsRepository plotsRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	public void addRole() {
		// TODO Auto-generated method stub
		Role role1 = new Role();
		role1.setRoleName("Admin");
		roleRepository.save(role1);

		Role role2 = new Role();
		role2.setRoleName("Agency");
		roleRepository.save(role2);

		Role role3 = new Role();
		role3.setRoleName("Agent");
		roleRepository.save(role3);

		Role role4 = new Role();
		role4.setRoleName("Customer");
		roleRepository.save(role4);
	}

	@Override
	public AdminDto addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findById("Admin").get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		admin.setRoles(roles);
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		Admin admin2 = adminRepository.save(admin);
		log.info("Admin Created Successfully..!!! ");

		AdminDto adminDto = userMapper.toAdminDto(admin2);
		log.info("Converted into AdminDto..!!");
		return adminDto;
	}

	@Override
	public AgencyDto addAgency(Agency agency) throws Exception {
		// TODO Auto-generated method stub
		String password=agency.getPassword();
		Role role = roleRepository.findById("Agency").get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		agency.setRoles(roles);
		agency.setPassword(bCryptPasswordEncoder.encode(agency.getPassword()));
		agency.setRegistrationDate(LocalDate.now());
		agency.setStatus("Active");
		Agency agency2 = agencyRepository.save(agency);
		emailUtil.sendAgencyRegistration(agency2.getEmail(),password);
		log.info("Agency created successfully..!!");

		AgencyDto agencyDto = userMapper.toAgencyDto(agency2);
		log.info("Converted into AgencyDto..!!");
		return agencyDto;
	}

	@Override
//	@CachePut(value = "admin", key = "#email")
	public AdminDto updateAdmin(String email, Admin admin) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Admin admin2 = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with Email :" + email));
		admin2.setAdminName(admin.getAdminName());
		admin2.setEmail(admin.getEmail());
		admin2.setPhoneNumber(admin.getPhoneNumber());
		Admin admin3 = adminRepository.save(admin2);
		log.info("Admin Updated successfully..!!");

		AdminDto adminDto = userMapper.toAdminDto(admin3);
		log.info("Converted into AdminDto..!!");
		return adminDto;
	}

	@Override
//	@Cacheable(value = "admin", key ="#email",unless = "#result==null")
	public AdminDto getAdmin(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Admin admin2 = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with Email :" + email));
		log.info("Admin data coming from DataBase..!!");
		AdminDto adminDto = userMapper.toAdminDto(admin2);
		return adminDto;
	}

	@Override
//	@CachePut(value = "agency", key = "#email")
	public AgencyDto updateAgency(String email, Agency agency) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
		agency2.setAgencyAddress(agency.getAgencyAddress());
		agency2.setAgencyName(agency.getAgencyName());
		agency2.setAgencyPinCode(agency.getAgencyPinCode());
		agency2.setEmail(agency.getEmail());
		agency2.setPhoneNumber(agency.getPhoneNumber());
		agency2.setCity(agency.getCity());
		agency2.setFbUrl(agency.getFbUrl());
		agency2.setInstagramUrl(agency.getInstagramUrl());
		agency2.setTwitterUrl(agency.getTwitterUrl());
		agency2.setState(agency.getState());
		Agency agency3 = agencyRepository.save(agency2);

		AgencyDto agencyDto = userMapper.toAgencyDto(agency3);
		return agencyDto;
	}

	@Override
//	@Cacheable(value = "agency", key = "#email")
	public AgencyDto getAgency(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency orElseThrow = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
		log.info("Coming from Database..!!");
		AgencyDto agencyDto = userMapper.toAgencyDto(orElseThrow);
		return agencyDto;
	}

	@Override
//	@CacheEvict(value = "agency", key = "#email")
	public String deleteAgency(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
		RealEStateUser byEmail = userRepo.findByEmail(email);
		
		byEmail.getRoles().clear(); // This will break the relation
	    userRepo.save(byEmail);
	    
		userRepo.delete(byEmail);
		agencyRepository.delete(agency);
		return "Agency got deleted..!!!";
	}

	@Override
//	@Cacheable(value = "customer")
	public List<CustomerDto> getAllCustomers() {
		// TODO Auto-generated method stub
		List<Customer> all = customerRepository.findAll();

		List<CustomerDto> collect = all.stream().map(userMapper::toCustomerDto).collect(Collectors.toList());
		return collect;
	}

	@Override
//	@Cacheable(value = "cutomer", key = "agencyName")
	public List<CustomerDto> getCustomersByAgency(String agencyName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByAgencyName(agencyName)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyName));
		List<Customer> byAgency = customerRepository.findByAgencyName(agency2.getAgencyName());

		List<CustomerDto> collect = byAgency.stream().map(userMapper::toCustomerDto).collect(Collectors.toList());
		return collect;
	}

	@Override
//	@Cacheable(value = "agent")
	public List<AgentDto> getAllAgents() {
		// TODO Auto-generated method stub
		List<Agent> all = agentRepository.findAll();
		List<AgentDto> collect = all.stream().map(userMapper::toAgentDto).collect(Collectors.toList());
		return collect;
	}

	@Override
//	@Cacheable(value = "agent", key = "#agencyName")
	public List<AgentDto> getAgentsByAgency(String agencyName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByAgencyName(agencyName)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyName));
		List<Agent> list = agentRepository.findByAgencyId(agency2.getId());

		List<AgentDto> collect = list.stream().map(userMapper::toAgentDto).collect(Collectors.toList());
		return collect;
	}

	@Override
	@Cacheable(value = "agency")
	public List<AgencyDto> getAllAgencies() {
		// TODO Auto-generated method stub
		List<Agency> all = agencyRepository.findAll();
		log.info("Fetching from DB..!!");
		List<AgencyDto> collect = all.stream().map(userMapper::toAgencyDto).collect(Collectors.toList());
		return collect;
	}

	@Override
	public VentureDto addVenture(Venture venture) {
		// TODO Auto-generated method stub
		Venture save = ventureRepository.save(venture);
		if(venture.getPlots()!=null) {
			for (Plots plot : venture.getPlots()) {
//				plot.setStatus(PropertyStatus.AVAILABLE);
				plot.setVenture(save);
				plotsRepository.save(plot);
			}
		}
		VentureDto landPropertyDto = userMapper.toVentureDto(save);
		return landPropertyDto;
	}

	@Override
//	@CachePut(value = "venture",key = "#id")
	public VentureDto updateVenture(long id, Venture landProperty) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Venture property = ventureRepository.findById(id)
				.orElseThrow(() -> new PropertyNotFoundException("Property not found with Id :" + id));
		property.setVentureName(landProperty.getVentureName());
		property.setAddress(landProperty.getAddress());
		property.setPhno(landProperty.getPhno());

		Venture landProperty2 = ventureRepository.save(property);
		VentureDto landPropertyDto = userMapper.toVentureDto(landProperty2);

		return landPropertyDto;
	}

	@Override
//	@Cacheable(value = "venture", key = "#id")
	public VentureDto getVenture(long id) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Venture property = ventureRepository.findById(id)
				.orElseThrow(() -> new PropertyNotFoundException("Property not found with Id :" + id));
		VentureDto landPropertyDto = userMapper.toVentureDto(property);
		return landPropertyDto;
	}
	
	@Override
//	@CacheEvict(value = "venture", key = "#id")
	public String deleteVenture(long id) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Venture property = ventureRepository.findById(id)
				.orElseThrow(() -> new PropertyNotFoundException("Property not found with Id :" + id));
		List<Plots> list = plotsRepository.findByVentureVentureId(property.getVentureId());
		for (Plots plots : list) {
			plotsRepository.delete(plots);
		}
		ventureRepository.delete(property);
		return "venture Deleted successfully...!!";
	}

	@Override
//	@Cacheable(value = "venture")
	public List<VentureDto> getAllVentures() {
		// TODO Auto-generated method stub
		List<Venture> list = ventureRepository.findAll();
		log.info("Fetching from DB..!!");
		List<VentureDto> collect = list.stream().map(userMapper::toVentureDto).collect(Collectors.toList());
		return collect;
	}

	@Override
	public PlotsDto addPlot(long ventureId, Plots plots) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Venture property = ventureRepository.findById(ventureId)
				.orElseThrow(() -> new PropertyNotFoundException("Property not found with Id :" + ventureId));
		plots.setVenture(property);
		Plots plots2 = plotsRepository.save(plots);
		PlotsDto pLotsDto = userMapper.toPLotsDto(plots2);
		return pLotsDto;
	}

	@Override
	public List<PlotsDto> addAllPlots(long ventureId, List<Plots> plots) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Venture venture = ventureRepository.findById(ventureId)
				.orElseThrow(() -> new PropertyNotFoundException("Property not found with Id :" + ventureId));
		List<Plots> collect = plots.stream().peek((plot) -> plot.setVenture(venture))
				.collect(Collectors.toList());
		
		List<Plots> list = plotsRepository.saveAll(collect);

		return list.stream().map(userMapper::toPLotsDto).collect(Collectors.toList());
	}

	@Override
	public PlotsDto updatePlot(long plotId, Plots plots) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Plots plots2 = plotsRepository.findById(plotId)
				.orElseThrow(() -> new PropertyNotFoundException("Plot not found with Id :" + plotId));
		plots2.setPlotNumber(plots.getPlotNumber());
		plots2.setPlotSize(plots.getPlotSize());
		plots2.setPrice(plots.getPrice());
		plots2.setStatus(plots.getStatus());
		plots2.setLocation(plots.getLocation());
		plots2.setFacing(plots.getFacing());
		plots2.setCornerPlot(plots.isCornerPlot());

		Plots save = plotsRepository.save(plots2);
		PlotsDto pLotsDto = userMapper.toPLotsDto(save);
		return pLotsDto;
	}

	@Override
	public PlotsDto getPlot(long plotId) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Plots plots2 = plotsRepository.findById(plotId)
				.orElseThrow(() -> new PropertyNotFoundException("Plot not found with Id :" + plotId));
		return userMapper.toPLotsDto(plots2);
	}

	@Override
	public String deletePlot(long plotId) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		Plots plots2 = plotsRepository.findById(plotId)
				.orElseThrow(() -> new PropertyNotFoundException("Plot not found with Id :" + plotId));
		plotsRepository.delete(plots2);
		return "Plot deleted..!!";
	}

	@Override
	public Long countOfVentures() {
		// TODO Auto-generated method stub
		return ventureRepository.count();
	}

	@Override
	public Long countOfAgencies() {
		// TODO Auto-generated method stub
		return agencyRepository.count();
	}

	@Override
	public Long countOfAgents() {
		// TODO Auto-generated method stub
		return agentRepository.count();
	}

	@Override
	public Long countOfCustomers() {
		// TODO Auto-generated method stub
		return customerRepository.count();
	}
}
