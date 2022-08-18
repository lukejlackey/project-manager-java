package com.lukejlackey.projectmanager.models;

import java.time.LocalDate;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Title field is required.")
	@Size(min=3, max=200, message="Must be between 3 and 200 characters.")
	private String title;
	
	@NotNull(message = "Due Date field is required.")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@FutureOrPresent(message = "Must be a date in the future.")
	private LocalDate dueDate;

	@NotEmpty(message = "Description field is required.")
	@Size(min=10, max=500, message="Must be between 10 and 500 characters.")
	private String description;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    	name="teamMembers",
    	joinColumns=@JoinColumn(name="project_id"),
    	inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private List<User> teamMembers;
    
	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	private List<ProjectTask> tasks;
	
	public Project() {
		this.teamMembers = new ArrayList<User>();
		this.tasks = new ArrayList<ProjectTask>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public List<User> getTeamMembers() {
		return teamMembers;
	}
	
	public List<String> getTeamMemberNames() {
		List<String> names = new ArrayList<String>();
		for(User member : teamMembers) {
			names.add(member.getFullName());
		}
		return names;
	}

	public void setTeamMembers(List<User> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
	public void addTeamMember(User teamMember) {
		if(!this.teamMembers.contains(teamMember)) this.teamMembers.add(teamMember);
	}
	
	public void removeTeamMember(User teamMember) {
		if(this.teamMembers.contains(teamMember)) this.teamMembers.remove(teamMember);
	}

	public List<ProjectTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<ProjectTask> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(ProjectTask task) {
		this.tasks.add(task);
	}

}