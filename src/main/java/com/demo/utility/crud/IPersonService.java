/**
 * 
 */
package com.demo.utility.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.demo.model.Person;

/**
 * @author vamsi
 *
 */
public interface IPersonService {

	@Query("select * from persons")
	public List<Person> findAllPersons();

	@Query("selectc * form persons=:firstName")
	public List<Person> findByFstName();

	Person createPerson(Person person);
}