package com.example.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity to mapping TB_PEOPLE<br>
 * Extract the auth to abstract entity
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 19/02/2018 22:05:29
 */
@Entity
@Table(name = "TB_PEOPLE")
@Audited
@AuditTable("TBH_PEOPLE")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = PersonBaseEntity.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "code")
public class Person extends PersonBaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5076342277118584038L;

	@Id
	@Column(name = "PK_PERSON")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long code;

	@OneToMany(mappedBy = "person", cascade = { CascadeType.ALL })
	@AuditMappedBy(mappedBy = "person")
	@NotAudited
	private List<Address> address;

	/**
	 * @param code
	 */
	public Person(Long code) {
		super();
		this.code = code;
	}

	/**
	 * no args
	 */
	public Person() {
		super();
	}

	/**
	 * @return the address
	 */
	public List<Address> getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(List<Address> address) {
		this.address = address;
	}

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

}
