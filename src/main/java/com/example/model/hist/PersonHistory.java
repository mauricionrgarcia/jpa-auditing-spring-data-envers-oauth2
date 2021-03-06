package com.example.model.hist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.model.PersonBaseEntity;

/**
 * Entity to mapping TB_PEOPLE<br>
 * Extract the auth to abstract entity
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 19/02/2018 22:05:29
 */
@Entity
@Table(name = "TBH_PEOPLE")
public class PersonHistory extends PersonBaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6229295677743605548L;

	/**
	 * @param code identifier
	 */
	public PersonHistory(Long code) {
		super();
		this.historyId = new PersonHistoryId();
		this.historyId.setCode(code);
	}

	/**
	 * Contructor no args
	 */
	public PersonHistory() {
		super();
	}

	/**
	 * Id tb history
	 */
	@EmbeddedId
	private PersonHistoryId historyId;

	/**
	 * @return the historyId
	 */
	public PersonHistoryId getHistoryId() {
		return historyId;
	}

	/**
	 * @param historyId the historyId to set
	 */
	public void setHistoryId(PersonHistoryId historyId) {
		this.historyId = historyId;
	}

}

/**
 * Pk
 * 
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 18:23:21
 */
@Embeddable
class PersonHistoryId implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6102328936787278408L;

	/**
	 * identifier of revision
	 */
	@Column(name = "REV")
	private Long rev;

	/**
	 * identifier of person
	 */
	@Column(name = "PK_PERSON")
	private Long code;

	/**
	 * @return the rev
	 */
	public Long getRev() {
		return rev;
	}

	/**
	 * @param rev the rev to set
	 */
	public void setRev(Long rev) {
		this.rev = rev;
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