package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

/**
 * Entity to mapping TB_ADDRESS<br>
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 02:00:19
 */
@MappedSuperclass
public class AddressBaseEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7818867141894929767L;

	/**
	 * street
	 */
	@Column(name = "TX_ADDRESS_STREET")
	private String street;

	/**
	 * number
	 */
	@Column(name = "TX_ADDRESS_NUMBER")
	private String number;

	/**
	 * district
	 */
	@Column(name = "TX_ADDRESS_DISTRICT")
	private String district;

	/**
	 * zipCode
	 */
	@Column(name = "TX_ADDRESS_ZIP_CODE")
	private String zipCode;

	/**
	 * city
	 */
	@Column(name = "TX_ADDRESS_CITY")
	private String city;

	/**
	 * state
	 */
	@Column(name = "TX_ADDRESS_STATE")
	private String state;

	/**
	 * person relationship
	 */
	@ManyToOne()
	@JoinColumn(name = "FK_PERSON")
	@JsonIdentityReference(alwaysAsId = true)
	private Person person;

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [street=" + street + ", number=" + number + ", district=" + district + ", zipCode=" + zipCode
				+ ", city=" + city + ", state=" + state + "]";
	}

}
