package com.websystique.springmvc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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

//	@NotNull
//	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Unit.class)
//	@JoinColumns({
//			@JoinColumn(name = "unit_user_id", nullable = false, referencedColumnName = "user_id"),
//			@JoinColumn(name = "unit_branch_id", nullable = false, referencedColumnName = "branch_id") })
//	private Unit unit;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "registrator", nullable = false)
	private User registrator;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Branch.class)
	@JoinColumn(name = "reg_branch", nullable = false)
	private Branch registratorBranch;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RegStatus.class)
	@JoinColumn(name = "regStatus_id", nullable = false)
	private RegStatus regStatus;

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
}
