package com.lukejlackey.projectmanager.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukejlackey.projectmanager.models.Project;
import com.lukejlackey.projectmanager.models.User;
import com.lukejlackey.projectmanager.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepo;

	public List<Project> findAll() {
		return projectRepo.findAll();
	}
	
	public Project findById(Long id) {
		Optional<Project> optionalProject = projectRepo.findById(id);
		if(optionalProject.isPresent()) {
			return optionalProject.get();
		}
		return null;
	}
	
	public List<Project> findYourProjects(Long id) {
		return projectRepo.findByTeamMembers_IdIs(id);
	}
	
	public List<Project> findOtherProjects(User user) {
		return projectRepo.findAllByTeamMembersNotContaining(user);
	}
	
	public Project createProject(Project newProject) {
		return projectRepo.save(newProject);
	}
	
	public Project editProject(Project project) {
		return projectRepo.save(project);
	}
	
	public void deleteProject(Project project) {
		projectRepo.delete(project);
		return;
	}
	
}