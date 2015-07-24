package com.websystique.springmvc.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Branch")
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(max = 45)
	@Column(name = "name", nullable = true)
	private String name;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinColumn(name = "manager_id", nullable = false)
	private User manager;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Unit.class)
	private Set<Unit> agentUnits = new TreeSet<Unit>();

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

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Set<Unit> getAgentUnits() {
		return agentUnits;
	}

	public void setAgentUnits(Set<Unit> agentUnits) {
		this.agentUnits = agentUnits;
	}

}
