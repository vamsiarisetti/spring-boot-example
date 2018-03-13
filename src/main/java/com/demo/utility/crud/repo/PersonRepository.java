/**
 * 
 */
package com.demo.utility.crud.repo;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.Person;

/**
 * @author vamsi
 *
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
	Person findByFirstName(String firstName);
}