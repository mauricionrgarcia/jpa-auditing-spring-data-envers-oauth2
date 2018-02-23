package com.example.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entity to mapping TB_PEOPLE<br>
 * Extract the auth to abstract entity
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 19/02/2018 22:05:29
 */
@Entity
@Audited
@Table(name = "TB_PEOPLE")
@AuditTable("TBH_PEOPLE")
@EntityListeners(AuditingEntityListener.class)
public class Person implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1307503170517288448L;

	@Id
	@Column(name = "PK_PERSON")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long code;

	@NotEmpty
	@Size(max = 50, min = 4)
	@Column(name = "TX_NAME", length = 50, nullable = false)
	private String name;

	@Column(name = "DT_BIRTH")
	private LocalDate dtBirth;

	@Column(name = "ID_GENRE")
	@Enumerated(EnumType.STRING)
	@NotNull
	private GenreEnum genre;

	@Column(name = "ID_ACTIVE")
	private String active;

	@Column(name = "DT_LAST_CHANGE")
	@LastModifiedDate
	private LocalDate dtLastChange;

	@Column(name = "ID_USER")
	@LastModifiedBy
	private Long userChange;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDtBirth() {
		return dtBirth;
	}

	public void setDtBirth(LocalDate dtBirth) {
		this.dtBirth = dtBirth;
	}

	public GenreEnum getGenre() {
		return genre;
	}

	public void setGenre(GenreEnum genre) {
		this.genre = genre;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public LocalDate getDtLastChange() {
		return dtLastChange;
	}

	public void setDtLastChange(LocalDate dtLastChange) {
		this.dtLastChange = dtLastChange;
	}

	public Long getUserChange() {
		return userChange;
	}

	public void setUserChange(Long userChange) {
		this.userChange = userChange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [code=" + code + ", name=" + name + ", dtBirth=" + dtBirth + ", genre=" + genre + ", active="
				+ active + ", dtLastChange=" + dtLastChange + ", userChange=" + userChange + "]";
	}

}
