package com.websystique.springmvc.model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Registration")
public class Registration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Size(max = 15)
	private String ico;

	@Column(name = "companyName", nullable = true)
	@Size(max = 45)
	private String companyName;

	@Id
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name = "reg_date")
	private LocalDate regDate;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false)
	private User registrator;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Branch.class)
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch registratorBranch;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RegStatus.class)
	@JoinColumn(name = "regStatus_id", nullable = false)
	private RegStatus regStatus;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity= Note.class)
	private Set<Note> notes = new TreeSet<Note>();

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public LocalDate getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	public RegStatus getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(RegStatus regStatus) {
		this.regStatus = regStatus;
	}

	public User getRegistrator() {
		return registrator;
	}

	public void setRegistrator(User registrator) {
		this.registrator = registrator;
	}

	public Branch getRegistratorBranch() {
		return registratorBranch;
	}

	public void setRegistratorBranch(Branch registratorBranch) {
		this.registratorBranch = registratorBranch;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}
}
