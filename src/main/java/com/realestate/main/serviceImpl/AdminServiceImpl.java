package com.realestate.main.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgencyVentureDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.PlotsDetailsDto;
import com.realestate.main.dto.PlotsDto;
import com.realestate.main.dto.RealEStateUserDto;
import com.realestate.main.dto.VentureDto;
import com.realestate.main.emailConfiguration.EmailUtil;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.AgencyVenture;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Plots;
import com.realestate.main.entity.PropertyStatus;
import com.realestate.main.entity.RealEStateUser;
import com.realestate.main.entity.Role;
import com.realestate.main.entity.Venture;
import com.realestate.main.excelOperations.PlotExcelService;
import com.realestate.main.exceptions.AgencyNotFoundException;
import com.realestate.main.exceptions.DuplicateEntryException;
import com.realestate.main.exceptions.PropertyNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.mapper.UserMapper;

import com.realestate.main.repository.AdminRepository;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgencyVentureRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.PlotsRepository;
import com.realestate.main.repository.RealEstateUserRepo;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.repository.VentureRepository;
import com.realestate.main.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
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
	
	@Autowired
	private PlotExcelService plotExcelService;

	@Autowired
	private AgencyVentureRepository agencyVentureRepository;

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
	public AdminDto addAdmin(Admin admin) throws DuplicateEntryException {
		// TODO Auto-generated method stub
		if(userRepo.existsByEmail(admin.getEmail())) throw new DuplicateEntryException("Email Already Exists with :"+admin.getEmail());
		if(userRepo.existsByPhoneNumber(admin.getPhoneNumber())) throw new DuplicateEntryException("Phone Number Already exists :"+admin.getPhoneNumber());
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
		if(userRepo.existsByEmail(agency.getEmail())) throw new DuplicateEntryException("Email Already Exists with :"+agency.getEmail());
		if(userRepo.existsByPhoneNumber(agency.getPhoneNumber())) throw new DuplicateEntryException("Phone Number Already exists :"+agency.getPhoneNumber());
		String password = agency.getPassword();
		Role role = roleRepository.findById("Agency").get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		agency.setRoles(roles);
		agency.setPassword(bCryptPasswordEncoder.encode(agency.getPassword()));
		agency.setRegistrationDate(LocalDate.now());
		agency.setStatus("ACTIVE");
		Agency agency2 = agencyRepository.save(agency);
		emailUtil.sendAgencyRegistration(agency2.getEmail(), password);
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

//	@Override
//	public AgencyDto updateAgency(String email, Agency agency) throws UserNotFoundException {
//		// TODO Auto-generated method stub
//		Agency agency2 = agencyRepository.findByEmail(email)
//				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
//		agency2.setAgencyAddress(agency.getAgencyAddress());
//		agency2.setAgencyName(agency.getAgencyName());
//		agency2.setAgencyPinCode(agency.getAgencyPinCode());
//		agency2.setEmail(agency.getEmail());
//		agency2.setPhoneNumber(agency.getPhoneNumber());
//		agency2.setCity(agency.getCity());
//		agency2.setFbUrl(agency.getFbUrl());
//		agency2.setInstagramUrl(agency.getInstagramUrl());
//		agency2.setTwitterUrl(agency.getTwitterUrl());
//		agency2.setState(agency.getState());
//		agency2.setStatus(agency.getStatus());
//		Agency agency3 = agencyRepository.save(agency2);
//
//		AgencyDto agencyDto = userMapper.toAgencyDto(agency3);
//		return agencyDto;
//	}

	
	
	//working method
	@Override
	public AgencyDto updateAgency(String email, Agency agency) throws UserNotFoundException {
	    Agency agency2 = agencyRepository.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("Agency not found with email: " + email));

	    // Only update non-null and valid fields
	    if (agency.getAgencyName() != null) agency2.setAgencyName(agency.getAgencyName());
	    if (agency.getAgencyAddress() != null) agency2.setAgencyAddress(agency.getAgencyAddress());
	    if (agency.getAgencyPinCode() != 0) agency2.setAgencyPinCode(agency.getAgencyPinCode());
	    if (agency.getPhoneNumber() != 0) agency2.setPhoneNumber(agency.getPhoneNumber());
	    if (agency.getCity() != null) agency2.setCity(agency.getCity());
	    if (agency.getState() != null) agency2.setState(agency.getState());
	    if (agency.getFbUrl() != null) agency2.setFbUrl(agency.getFbUrl());
	    if (agency.getInstagramUrl() != null) agency2.setInstagramUrl(agency.getInstagramUrl());
	    if (agency.getTwitterUrl() != null) agency2.setTwitterUrl(agency.getTwitterUrl());
	    if (agency.getStatus() != null) agency2.setStatus(agency.getStatus());

	    // Do NOT update email here — it's your identifier
	    // Do NOT update password or roles unless explicitly handled

	    Agency agency3 = agencyRepository.save(agency2);
	    return userMapper.toAgencyDto(agency3);
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
//	@Cacheable(value = "agency")
	public List<AgencyDto> getAllAgencies() {
		// TODO Auto-generated method stub
		List<Agency> all = agencyRepository.findAll();
		log.info("Fetching from DB..!!");
		List<AgencyDto> collect = all.stream().map(userMapper::toAgencyDto).collect(Collectors.toList());
		return collect;
	}

	@Override
	public VentureDto addVenture(Venture venture, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		Venture save = ventureRepository.save(venture);
		List<Plots> plots = plotExcelService.parseAndValidate(file, save);

		long availablePlots = plots.stream().filter(plot -> plot.getStatus() == PropertyStatus.AVAILABLE).count();
		long soldPlots = plots.stream().filter(plot -> plot.getStatus() == PropertyStatus.SOLD).count();
		long bookedPlots = plots.stream().filter(plot -> plot.getStatus() == PropertyStatus.BOOKED).count();

		save.setAvailablePlots(availablePlots);
		save.setSoldPlots(soldPlots);
		save.setBookedPlots(bookedPlots);
		save.setTotalPlots(plots.size());

		save.setVentureStatus(PropertyStatus.ACTIVE);
		Venture venture2 = ventureRepository.save(save);
		VentureDto landPropertyDto = userMapper.toVentureDto(venture2);
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
		property.setVentureStatus(landProperty.getVentureStatus());

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
	public List<PlotsDto> addAllPlots(long ventureId, MultipartFile file)
			throws PropertyNotFoundException, IOException {
		// TODO Auto-generated method stub
		Venture venture = ventureRepository.findById(ventureId)
				.orElseThrow(() -> new PropertyNotFoundException("Property not found with Id :" + ventureId));
		List<Plots> list = plotExcelService.parseAndValidate(file, venture);
		plotsRepository.saveAll(list);
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

	@Override
	public AgencyVentureDto assignVentureWithPlots(long agencyId, long ventureId, long startPlotNumber,
			long endPlotNumber) throws AgencyNotFoundException, PropertyNotFoundException {
		// TODO Auto-generated method stub
		Agency agency = agencyRepository.findById(agencyId)
				.orElseThrow(() -> new AgencyNotFoundException("Agency Not Found with :" + agencyId));
		Venture venture = ventureRepository.findById(ventureId)
				.orElseThrow(() -> new PropertyNotFoundException("Property Not Found with :" + ventureId));
		List<Plots> plots = plotsRepository.findByVentureVentureIdAndPlotNumberBetweenOrderByPlotNumber(ventureId,
				startPlotNumber, endPlotNumber);

		if (plots.isEmpty()) {
			throw new PropertyNotFoundException("Plots Not found within the given range..!!");
		}

		for (Plots plots2 : plots) {
			plots2.setAssignStatus(PropertyStatus.ASSIGNED);
			plots2.setAgencyName(agency.getAgencyName());
		}

		AgencyVenture agencyVenture = new AgencyVenture();
		agencyVenture.setAgencies(agency);
		agencyVenture.setVenture(venture);
		agencyVenture.setPlotsAssigned(plots);
		agencyVenture.setNumberOfPlots(plots.size());

		plotsRepository.saveAll(plots);
		AgencyVenture save = agencyVentureRepository.save(agencyVenture);
		AgencyVentureDto agencyVentureDto = userMapper.toAgencyVentureDto(save);
		return agencyVentureDto;
	}

	@Override
	public PlotsDetailsDto countPlotsByVentureId(long ventureId) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		List<Plots> plots = plotsRepository.findByVentureVentureId(ventureId);
		if (plots.isEmpty()) {
			throw new PropertyNotFoundException("Plots not found with venture Id :" + ventureId);
		}

		long total = plots.size();
		long unassigned = 0, assigned = 0, booked = 0, sold = 0, available = 0;

		for (Plots plot : plots) {
			PropertyStatus status = plot.getAssignStatus();
			if (status == PropertyStatus.NOTASSIGNED)
				unassigned++;
			else if (status == PropertyStatus.ASSIGNED)
				assigned++;
			else if (status == PropertyStatus.BOOKED)
				booked++;
			else if (status == PropertyStatus.SOLD)
				sold++;
			else if (status == PropertyStatus.AVAILABLE)
				available++;
		}

		PlotsDetailsDto dto = new PlotsDetailsDto();
		dto.setCountOfPlots(total);
		dto.setCountOfAvailablePlots(available);
		dto.setCountOfBookedPlots(booked);
		dto.setCountOfAssignedPlots(assigned);
		dto.setCountOfSoldPlots(sold);
		dto.setCountOfUnAssignedPlots(unassigned);

		return dto;
	}

	@Override
	public List<Plots> getPlotsByVentureId(long ventureId) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		List<Plots> plots = plotsRepository.findByVentureVentureId(ventureId);
		if (plots.isEmpty()) {
			throw new PropertyNotFoundException("Plots not found with venture Id :" + ventureId);
		}
		return plots;
	}

	@Override
	public List<Plots> getUnAssignedPlots(long ventureId) throws PropertyNotFoundException {
		// TODO Auto-generated method stub
		List<Plots> list = plotsRepository.findByVentureVentureId(ventureId);
		if (list.isEmpty())
			throw new PropertyNotFoundException("Plots not found with venture id :" + ventureId);
		List<Plots> list2 = list.stream().filter((plot) -> plot.getAssignStatus().equals(PropertyStatus.NOTASSIGNED))
				.collect(Collectors.toList());
		if (list2.isEmpty())
			throw new PropertyNotFoundException("No Plots found with NotAssigned Status..!!");
		return list2;
	}

	@Override
	public RealEStateUser getUserByEmail(String email) throws UserNotFoundException {
		RealEStateUser byEmail = userRepo.findByEmail(email);
		if(byEmail==null) {
			throw new UserNotFoundException("user with email :"+email+" is not found.......");
		}
		
		return byEmail;
	}


	public List<Agency> getActiveAgencies() {
		// TODO Auto-generated method stub
		List<Agency> allByStatus = agencyRepository.findAllByStatus("ACTIVE");
		return allByStatus;
	}

	@Override
	public List<Venture> getActiveVentures() {
		// TODO Auto-generated method stub
		List<Venture> allByVentureStatus = ventureRepository.findAllByVentureStatus("ACTIVE");
		return allByVentureStatus;
	}

//	@Override
//	public long countUnassignedPlotsByVentureId(long ventureId) throws PropertyNotFoundException {
//		// TODO Auto-generated method stub
//		List<Plots> byVentureVentureId = plotsRepository.findByVentureVentureId(ventureId);
//		long count = byVentureVentureId.stream().filter((plot)-> plot.getAssignStatus().equals(PropertyStatus.NOTASSIGNED)).count();
//		return count;
//	}
//
//	@Override
//	public long countAssignedPlotsByVentureId(long ventureId) throws PropertyNotFoundException {
//		// TODO Auto-generated method stub
//		List<Plots> byVentureVentureId = plotsRepository.findByVentureVentureId(ventureId);
//		long count = byVentureVentureId.stream().filter((plot)-> plot.getAssignStatus().equals(PropertyStatus.ASSIGNED)).count();
//		return count;
//	}
}
