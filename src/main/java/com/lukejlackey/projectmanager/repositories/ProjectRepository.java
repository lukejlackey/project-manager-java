package com.lukejlackey.projectmanager.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lukejlackey.projectmanager.models.Project;
import com.lukejlackey.projectmanager.models.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	List<Project> findAll();
	
	List<Project> findByTeamMembers_IdIs(Long id);
	
	List<Project> findAllByTeamMembersNotContaining(User user);
	
}
