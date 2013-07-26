package com.osintegrators.example;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@NamedQueries(value = { @NamedQuery(name = "Address.findAll", query = "select a from Address a"),
						@NamedQuery(name = "Address.findCandidateFriends", query = "select a from Address as a where a.name <> ?1 and a._id not in ( select b.friend._id from Address2AddressLink as b where b.person.name = ?1 )")})
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long _id;

	private String name;
	private String address;
	private String phone;
	private String email;
	private String dob;
	private String last_present;

	@OneToMany( cascade=CascadeType.REMOVE, mappedBy = "person", fetch = FetchType.EAGER)
	private Set<Address2AddressLink> friends;

	@OneToMany( cascade=CascadeType.REMOVE, mappedBy = "friend", fetch = FetchType.EAGER)
	private Set<Address2AddressLink> inverseFriends;
	
	

	public Address(String name, String address, String phoneNumber,
			String email, String dob, String lastPresent) {
		this.name = name;
		this.address = address;
		this.phone = phoneNumber;
		this.email = email;
		this.dob = dob;
		this.last_present = lastPresent;
	}

	// ADDED Default Constructor Because of Build Errors in pom.xml
	public Address() {
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phoneNumber) {
		this.phone = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getLast_present() {
		return last_present;
	}

	public void setLast_present(String lastPresent) {
		this.last_present = lastPresent;
	}
	
	@JsonIgnore
	public Set<Address2AddressLink> getFriends()
	{
		return this.friends;
	}
	
	@JsonIgnore
	public Set<Address2AddressLink> getInverseFriends()
	{
		return this.friends;
	}
}
