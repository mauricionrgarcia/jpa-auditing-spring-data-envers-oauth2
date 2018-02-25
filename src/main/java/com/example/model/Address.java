package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entity Address<br>
 *
 * @author <a href="mailto:mauricionrgarcia@gmail.com">Mauricio</a>
 * @version
 * @sinse 25/02/2018 02:00:19
 */
@Entity
@Table(name = "TB_ADDRESS")
@Audited
@AuditTable("TBH_ADDRESS")
@EntityListeners(AuditingEntityListener.class)
@AuditOverride(forClass = AddressBaseEntity.class)
public class Address extends AddressBaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7818867141894929767L;

	/**
	 * identifier
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ADDRESS")
	private Long code;

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
