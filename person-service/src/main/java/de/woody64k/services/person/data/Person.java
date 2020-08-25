package de.woody64k.services.person.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String personalNumber;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public Date getBirthdate() {
	return birthdate;
    }

    public void setBirthdate(Date birthdate) {
	this.birthdate = birthdate;
    }

    public String getPersonalNumber() {
	return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
	this.personalNumber = personalNumber;
    }

}
