package com.lukejlackey.projectmanager.models;

import java.time.LocalDate;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tasks")
public class ProjectTask {
	
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
    @JoinColumn(name="poster_id")
    private User poster;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id")
    private Project project;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    	name="taskWorkers",
    	joinColumns=@JoinColumn(name="task_id"),
    	inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private List<User> taskWorkers;

	public ProjectTask() {
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

	public User getPoster() {
		return poster;
	}

	public void setPoster(User poster) {
		this.poster = poster;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<User> getTaskWorkers() {
		return taskWorkers;
	}

	public void setTaskWorkers(List<User> taskWorkers) {
		this.taskWorkers = taskWorkers;
	}
	
	public void addTaskWorker(User taskWorker) {
		this.taskWorkers.add(taskWorker);
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
