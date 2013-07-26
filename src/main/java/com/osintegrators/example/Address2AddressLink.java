package com.osintegrators.example;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="a2alink")
public class Address2AddressLink  {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long _id;
	
	
		@ManyToOne(optional = false)
	    private Address person;

		@ManyToOne(optional = false)
		private  Address friend;

		public Address getPerson() {
			return person;
		}

		public void setPerson(Address person) {
			this.person = person;
		}

		public Address getFriend() {
			return friend;
		}

		public void setFriend(Address friend) {
			this.friend = friend;
		}
	
		
		
}
