//package com.realestate.main.serviceImpl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import com.realestate.main.entity.Customer;
//import com.realestate.main.exceptions.UserNotFoundException;
//import com.realestate.main.repository.AgentRepository;
//import com.realestate.main.repository.CustomerRepository;
//
//@ExtendWith(MockitoExtension.class)
//public class AgentServiceImplTest {
//	
//	@Mock
//	private AgentRepository agentRepository;
//	
//	@Mock
//	private CustomerRepository customerRepository;
//	
//	@InjectMocks
//	private AgentServiceImpl agentServiceImpl;
//	
//	private Customer mockCustomer;
//
//	@BeforeEach
//	public void setUp() {
//		mockCustomer=new Customer();
//		String email="abc@gmail.com";
//		mockCustomer.setId(1);
//		mockCustomer.setEmail(email);
//		mockCustomer.setAddress("hyd");
//		mockCustomer.setAgencyId(1);
//		mockCustomer.setCity("Hyd");
//		mockCustomer.setPincode(500062);
//	}
//	
//	@Test
//	public void getCustomerSuccessTest() throws UserNotFoundException {
//		
//		when(customerRepository.findByEmail(mockCustomer.getEmail())).thenReturn(Optional.of(mockCustomer));
//		
//		Customer res = agentServiceImpl.getCustomer(mockCustomer.getEmail());
//		
//		assertEquals(mockCustomer.getEmail(), res.getEmail());
//		assertEquals(mockCustomer.getId(), res.getId());
//		assertEquals(mockCustomer.getAddress(), res.getAddress());
//		assertEquals(mockCustomer.getAgencyId(),res.getAgencyId());
//		assertNotNull(res);
//		
//		verify(customerRepository,times(1)).findByEmail(mockCustomer.getEmail());
//		//verify(agentServiceImpl,times(1)).getCustomer(mockCustomer.getEmail());
//	}
//	
//	@Test
//	public void getCustomerFailureTest() {
//		String email1="xyz@gmail.com";
//		when(customerRepository.findByEmail(email1)).thenReturn(Optional.empty());
//		
//		UserNotFoundException exception = assertThrows(UserNotFoundException.class,()->{
//			agentServiceImpl.getCustomer(email1);
//		});
//		
//		assertEquals("Customer Not found with :" + email1, exception.getMessage());
//		
//		verify(customerRepository,times(1)).findByEmail(email1);
//	}
//
//	@Test
//	public void updateCustomerSuccessTest() throws UserNotFoundException {
//		when(customerRepository.findByEmail(mockCustomer.getEmail())).thenReturn(Optional.of(mockCustomer));
//		
//		Customer updatedCustomer=new Customer();
//		updatedCustomer.setAddress("Kadapa");
//		updatedCustomer.setAgencyId(2);
//		updatedCustomer.setCity("Vizag");
//		updatedCustomer.setEmail(mockCustomer.getEmail());
//		
//		when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);
//		
//		Customer result = agentServiceImpl.updateCustomer(mockCustomer.getEmail(), updatedCustomer);
//		
//		assertEquals(updatedCustomer.getAddress(), result.getAddress());
//		assertEquals(updatedCustomer.getAgencyId(), result.getAgencyId());
//		assertEquals(updatedCustomer.getCity(), result.getCity());
//		assertNotNull(result);
//		
//		verify(customerRepository,times(1)).findByEmail(mockCustomer.getEmail());
//		verify(customerRepository,times(1)).save(any(Customer.class));
//		//verify(agentServiceImpl,times(1)).updateCustomer(mockCustomer.getEmail(), updatedCustomer);
//	}
//	
//	@Test
//	public void updateCustomerFailureTest() {
//		String email="xyz@gmail.com";
//		when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());
//		
//		UserNotFoundException exception = assertThrows(UserNotFoundException.class,()->{
//			agentServiceImpl.updateCustomer(email, mockCustomer);
//		});
//		
//		assertEquals("Customer Not found with :" + email, exception.getMessage());
//		verify(customerRepository,times(1)).findByEmail(email);
//	}
//}
