package com.lukejlackey.projectmanager.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "First Name is required.")
	@Size(min = 3, max = 100, message = "Must be between 3 and 100 characters.")
	private String firstName;

	@NotEmpty(message = "Last Name is required.")
	@Size(min = 3, max = 100, message = "Must be between 3 and 100 characters.")
	private String lastName;

	@NotEmpty(message = "Email is required.")
	@Email(message = "Please enter a valid email.")
	private String email;

	@NotEmpty(message = "Password is required.")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,128}$", message = "Must be between 8 and 128 characters and contain: 1 upper, 1 lower, 1 digit, 1 special character.")
	private String password;

	@Transient
	@NotEmpty(message = "Confirm Password is required.")
	private String confirm;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Project> projects;
	
	@OneToMany(mappedBy = "poster", fetch = FetchType.LAZY)
	private List<ProjectTask> tasksPosted;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    	name="teamMembers",
    	joinColumns=@JoinColumn(name="user_id"),
    	inverseJoinColumns=@JoinColumn(name="project_id")
    )
    private List<Project> activeProjects;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    	name="taskWorkers",
    	joinColumns=@JoinColumn(name="user_id"),
    	inverseJoinColumns=@JoinColumn(name="task_id")
    )
    private List<ProjectTask> activeTasks;
	
	public User() {
	}

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
	
	public String getFullName() {
		return firstName + " " + lastName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

}