package com.example.model.hist;

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

}
