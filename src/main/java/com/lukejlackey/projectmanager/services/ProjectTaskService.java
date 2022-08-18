package com.lukejlackey.projectmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukejlackey.projectmanager.models.ProjectTask;
import com.lukejlackey.projectmanager.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepo;
	
	public ProjectTask createProjectTask(ProjectTask newProjectTask) {
		return projectTaskRepo.save(newProjectTask);
	}

}
