package com.osintegrators.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	Address2AddressLinkRepository linkRepository;

	@PersistenceContext
	EntityManager entityManager;

	public void createAddress(Address address) {
		addressRepository.save(address);
	}

	public List<Address> getAllAddresses() {
		return addressRepository.findAll();

	}

	public List<Address> findFriends(Long id) {

		List<Address> friends = new ArrayList<Address>();

		Address person = addressRepository.findOne(id);

		Set<Address2AddressLink> friendAssociations = person.getFriends();

		for (Address2AddressLink link : friendAssociations) {
			friends.add(link.getFriend());
		}

		return friends;
	}


	public void deleteAddress(Address address) {
		
		address = addressRepository.findOne(address.get_id());

		System.out.println("deleting Address");
		addressRepository.delete(address);
	}

	public Address getAddressById(Long id) {
		return addressRepository.findOne(id);
	}

	public void updateAddress(Address address) {
		addressRepository.save(address);

	}

	public List<Address> findCandidateFriends(String name) {
		return addressRepository.findCandidateFriends(name);
	}

	public Address addFriendshipAssociation(Long personId, Long newFriendId) {

		System.out.println("addFriendAssociation");

		Address person = addressRepository.findOne(personId);
		Address newFriend = addressRepository.findOne(newFriendId);

		System.out.println("found users");

		Address2AddressLink associationOne = new Address2AddressLink();
		associationOne.setPerson(person);
		associationOne.setFriend(newFriend);
		person.getFriends().add(associationOne);
		linkRepository.save(associationOne);
		System.out.println("created associationOne");

		Address2AddressLink associationTwo = new Address2AddressLink();
		associationTwo.setPerson(newFriend);
		associationTwo.setFriend(person);
		newFriend.getFriends().add(associationTwo);
		linkRepository.save(associationTwo);
		System.out.println("created associationTwo");

		addressRepository.save(person);
		System.out.println("saved person");

		addressRepository.save(newFriend);
		System.out.println("saved newFriend");

		System.out.println("returning person");
		return person;
	}
	
	public List<String> findSuggestions(Long id) {
		
		List<String> suggestions = new ArrayList<String>();
	
		// lookup our user
		Address person = addressRepository.findOne(id);
		
		List<String> suggestionsFromFirstLevelFriends = new ArrayList<String>();
		List<String> suggestionsFromSecondLevelFriends = new ArrayList<String>();
		
		
		// find friends first
		Set<Address2AddressLink> friends = person.getFriends();
		for( Address2AddressLink friendLink : friends )
		{
			Address friend = friendLink.getFriend();
			
			System.out.println( "Person: " + person.getName() );
			System.out.println( "Friend: " + friend.getName() );
			
			String lastPresent = friend.getLast_present();
			
			System.out.println( "Last Present: " + lastPresent );
			
			// if there's a present and it isn't something we just got...
			if( lastPresent != null && !lastPresent.isEmpty() && !lastPresent.equals(person.getLast_present()))
			{
				System.out.println( "adding " + lastPresent + " to sugestionsFromFirstLevelFriends" );
				suggestionsFromFirstLevelFriends.add( lastPresent );
			}
			
			// now get friends of this friend
			Set<Address2AddressLink> secondLevelFriends = friend.getFriends(); // note: this will include the present person, since friendship is a bi-directional relationship
			for( Address2AddressLink secondLevelFriendLink : secondLevelFriends )
			{
				Address secondLevelFriend = secondLevelFriendLink.getFriend();
				if( secondLevelFriend.get_id().equals( person.get_id() ))
				{
					continue;
				}
				else
				{
					String secondLevelLastPresent = secondLevelFriend.getLast_present();
					
					System.out.println( "Second Level Friend: " + secondLevelFriend.getName() );
					System.out.println( "Second Level Last Present: " + secondLevelLastPresent );
					
					// if there's a present and it isn't something we just got...
					if( secondLevelLastPresent != null && !secondLevelLastPresent.isEmpty() && !secondLevelLastPresent.equals(person.getLast_present()))
					{
						System.out.println( "adding " + secondLevelLastPresent + " to suggestionsFromSecondLevelFriends" );
						suggestionsFromSecondLevelFriends.add( secondLevelLastPresent );
					}		
				}
				
			}
		}
		
		suggestions.addAll( suggestionsFromFirstLevelFriends );
		suggestions.addAll( suggestionsFromSecondLevelFriends );
		
		
		return suggestions;
	}

}
