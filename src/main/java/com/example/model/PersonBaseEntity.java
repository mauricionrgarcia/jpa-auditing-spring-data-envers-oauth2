package com.example.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Entity to mapping TB_PEOPLE<br>
 * Extract the auth to abstract entity
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 19/02/2018 22:05:29
 */
@MappedSuperclass
public class PersonBaseEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1307503170517288448L;

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
	private LocalDateTime dtLastChange;

	@Column(name = "ID_USER")
	@LastModifiedBy
	private Long userChange;

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

	public LocalDateTime getDtLastChange() {
		return dtLastChange;
	}

	public void setDtLastChange(LocalDateTime dtLastChange) {
		this.dtLastChange = dtLastChange;
	}

	public Long getUserChange() {
		return userChange;
	}

	public void setUserChange(Long userChange) {
		this.userChange = userChange;
	}

}
