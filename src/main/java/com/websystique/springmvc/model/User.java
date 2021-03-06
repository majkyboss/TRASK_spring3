package com.websystique.springmvc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "User")
public class User implements Serializable, Comparable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, max = 45)
	@Column(name = "name", nullable = true)
	private String name;

	@Size(min = 2, max = 45)
	@Column(name = "lastname", nullable = true)
	private String lastName;

	@Size(max = 45)
	@Column(name = "degree", nullable = true)
	private String degree;

	@NotNull
	@Size(max = 10)
	@Column(name = "birth_date", nullable = false)
	private String birthNumber;

	@NotNull
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Column(name = "date_in", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate dateIn;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Column(name = "date_out", nullable = true)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate dateOut;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Branch.class)
	private Branch currentBranch;

	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;

	@Column(name = "password_hash", nullable = false, length = 60)
	private String password;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getBirthNumber() {
		return birthNumber;
	}

	public void setBirthNumber(String birthNumber) {
		this.birthNumber = birthNumber;
	}

	public LocalDate getDateIn() {
		return dateIn;
	}

	public void setDateIn(LocalDate dateIn) {
		this.dateIn = dateIn;
	}

	public LocalDate getDateOut() {
		return dateOut;
	}

	public void setDateOut(LocalDate dateOut) {
		this.dateOut = dateOut;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		// return "User: id[" + id + "] name[" + name + "] lastName[" + lastName
		// + "] birthNumber[" + birthNumber + "] dateIn[" + dateIn
		// + "] dateOut[" + dateOut + "] role[" + role + "]";
		return name + " " + lastName;
	}

	@Override
	public int compareTo(User o) {
		return Integer.compare(id, o.id);
	}

	public Branch getCurrentBranch() {
		return currentBranch;
	}

	public void setCurrentBranch(Branch currentBranch) {
		this.currentBranch = currentBranch;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
