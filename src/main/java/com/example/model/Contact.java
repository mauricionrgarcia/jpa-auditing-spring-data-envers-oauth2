package com.example.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "TB_CONTACT")
@Audited
@AuditTable("TBH_CONTACT")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "code")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ADDRESS")
	private Long code;

	@ManyToOne
	@JoinColumn(name = "FK_PERSON")
	private Person person;

	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
	// @OneToMany(mappedBy = "contact")
	// @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Fone> fones;

	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
	private List<Email> emails;

	/**
	 * @return the code
	 */
	public Long getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Long code) {
		this.code = code;
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

	/**
	 * @return the fones
	 */
	public List<Fone> getFones() {
		return fones;
	}

	/**
	 * @param fones the fones to set
	 */
	public void setFones(List<Fone> fones) {
		this.fones = fones;
	}

	/**
	 * @return the emails
	 */
	public List<Email> getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

}
