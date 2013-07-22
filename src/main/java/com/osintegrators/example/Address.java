package com.osintegrators.example;

import java.util.List;
import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name = "Address.findAll", query = "select a from Address a"),
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long _id;

	private String name;
	private String address;
	private String phone;
	private String email;
	private String dob;
	private String lastPresent;

  @OneToMany
  @JoinTable(name="friend",
    @JoinColumns(name = "", referencedColumnName = ""))
  private List<Address> friends;
  @Transient
  private List<String> suggestions;

	public Address(String name, String address, String phoneNumber, String email, String dob, String lastPresent) {
		this.name = name;
		this.address = address;
		this.phone = phoneNumber;
		this.email = email;
		this.dob = dob;
		this.lastPresent = lastPresent;
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
	
	public String getLastPresent() {
		return lastPresent;
	}

	public void setLastPresent(String lastPresent) {
		this.lastPresent = lastPresent;
	}
}
