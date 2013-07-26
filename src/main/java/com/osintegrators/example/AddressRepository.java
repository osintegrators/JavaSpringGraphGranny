package com.osintegrators.example;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
	
	List<Address> findAll();

	/* List<Address> findFriends(String name); */

	List<Address> findCandidateFriends( String name );
}
