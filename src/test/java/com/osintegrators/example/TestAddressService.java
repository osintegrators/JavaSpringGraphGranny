package com.osintegrators.example;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dmistry
 * @author prhodes 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
public class TestAddressService {
	
	@Autowired
	AddressService addressService;
	
	
	Address address1;
	Address address2;
	Address address3;
	Address address4;
	Address address5;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	
		// create some address objects...
		List<Address> addresses = addressService.getAllAddresses();
		for( Address address : addresses )
		{
			addressService.deleteAddress(address);
		}
		
		
		address1 = new Address();
		
		address1.setName("Michael Hayes");
		address1.setAddress("Bad Street, Atlanta GA");
		address1.setDob("01/13/1963");
		address1.setEmail("pshayes@example.com");
		address1.setLast_present("Fire Truck");
		address1.setPhone("000-432-7344");
		addressService.createAddress(address1);
		
		address2 = new Address();
		
		address2.setName("Jimmy Garvin");
		address2.setAddress("123 Any Street");
		address2.setDob("7/13/1960");
		address2.setEmail("jgarvin@example.com");
		address2.setLast_present("Ray Gun");
		address2.setPhone("734-964-0023");
		addressService.createAddress(address2);

		address3 = new Address();

		address3.setName("Eddie Gilbert");
		address3.setAddress("44 Somewhere Avenue");
		address3.setDob("9/13/1959");
		address3.setEmail("egilbert@example.com");
		address3.setLast_present("Teddy Bear");
		address3.setPhone("627-000-4999");
		addressService.createAddress(address3);		

		address4 = new Address();
		
		address4.setName("Dusty Rhodes");
		address4.setAddress("111 Plumber Street");
		address4.setDob("8/09/1954");
		address4.setEmail("drhodes@example.com");
		address4.setLast_present("Truck");
		address4.setPhone("049-614-7282");	
		addressService.createAddress(address4);

		address5 = new Address();

		address5.setName("Terry Gordy");
		address5.setAddress("Bad Street, Atlanta GA");
		address5.setDob("2/14/1967");
		address5.setEmail("bambam@example.com");
		address5.setLast_present("Singlet");
		address5.setPhone("999-713-3322");		
		addressService.createAddress(address5);

		
		addressService.addFriendshipAssociation(address1.get_id(), address2.get_id());
		addressService.addFriendshipAssociation(address1.get_id(), address3.get_id());
		
		addressService.addFriendshipAssociation(address2.get_id(), address4.get_id());
		addressService.addFriendshipAssociation(address3.get_id(), address5.get_id());
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	
		// delete the address objects we are working with...
		List<Address> addresses = addressService.getAllAddresses();
		for( Address address : addresses )
		{
			addressService.deleteAddress(address);
		}
		
	}

	@Test
	public void testFindFriends() {
		
		
		List<Address> friends = addressService.findFriends(address1.get_id());
		assertNotNull( friends );
		assertTrue( friends.size() == 2 );
		
	}
	
	@Test
	public void testFinCandidateFriends() {
		
		List<Address> candidateFriends = addressService.findCandidateFriends(address1.getName());

		boolean foundDusty = false;
		boolean foundBamBam = false;
		for( Address address : candidateFriends )
		{
			if( address.getName().equals( "Dusty Rhodes" ))
				foundDusty = true;
			
			if( address.getName().equals( "Terry Gordy" ))
				foundBamBam = true;
		}
		
		assertTrue( foundDusty && foundBamBam );
	}
	
	@Test
	public void tesetFindSuggestions()
	{
		List<String> suggestions = addressService.findSuggestions(address1.get_id());
		
		// suggestion: Teddy Bear
		// suggestion: Ray Gun
		// suggestion: Singlet
		// suggestion: Truck
		boolean foundTeddyBear = false;
		boolean foundRayGun = false;
		boolean foundSinglet = false;
		boolean foundTruck = false;
		
		
		for( String suggestion : suggestions )
		{
			if( suggestion.equals( "Teddy Bear" ) )
			{
				foundTeddyBear = true;
			}
			if( suggestion.equals( "Ray Gun" ) )
			{
				foundRayGun = true;
			}
			if( suggestion.equals( "Singlet" ) )
			{
				foundSinglet = true;
			}
			if( suggestion.equals( "Truck" ) )
			{
				foundTruck = true;
			}
			
			
		}
		
		assertTrue( foundTeddyBear && foundRayGun && foundSinglet && foundTruck );
	}

}
