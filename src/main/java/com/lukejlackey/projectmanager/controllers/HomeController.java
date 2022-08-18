package com.lukejlackey.projectmanager.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lukejlackey.projectmanager.models.LoginUser;
import com.lukejlackey.projectmanager.models.Project;
import com.lukejlackey.projectmanager.models.ProjectTask;
import com.lukejlackey.projectmanager.models.User;
import com.lukejlackey.projectmanager.services.ProjectService;
import com.lukejlackey.projectmanager.services.ProjectTaskService;
import com.lukejlackey.projectmanager.services.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectTaskService projectTaskService;

	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null) {
			model.addAttribute("user", new User());
			model.addAttribute("loginUser", new LoginUser());
			return "loginReg.jsp";
		}
		return "redirect:/dashboard";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session) {
		User newUser = userService.registerUser(user, result);
		if (result.hasErrors() || newUser == null) {
			model.addAttribute("loginUser", new LoginUser());
			return "loginReg.jsp";
		}
		session.setAttribute("loggedUserId", newUser.getId());
		return "redirect:/dashboard";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginUser") LoginUser loginUser, BindingResult result, Model model,
			HttpSession session) {
		User loggedUser = userService.login(loginUser, result);
		if (result.hasErrors() || loggedUser == null) {
			model.addAttribute("user", new User());
			return "loginReg.jsp";
		}
		session.setAttribute("loggedUserId", loggedUser.getId());
		return "redirect:/dashboard";
	}

	@GetMapping("/dashboard")
	public String showDash(Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		model.addAttribute("loggedUser", user);
		model.addAttribute("otherProjects", projectService.findOtherProjects(user));
		model.addAttribute("yourProjects", projectService.findYourProjects((Long) session.getAttribute("loggedUserId")));
		return "dash.jsp";
	}
	
	@PutMapping("/dashboard")
	public String joinTeam(@RequestParam Long projectId, Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		Project project = projectService.findById(projectId);
		project.addTeamMember(user);
		projectService.editProject(project);
		return "redirect:/dashboard";
	}
	
	@DeleteMapping("/dashboard")
	public String leaveTeam(@RequestParam Long projectId, Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		Project project = projectService.findById(projectId);
		project.removeTeamMember(user);
		projectService.editProject(project);
		return "redirect:/dashboard";
	}

	@GetMapping("/projects/new")
	public String showNew(Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		model.addAttribute("loggedUser", userService.findById((Long) session.getAttribute("loggedUserId")));
		model.addAttribute("project", new Project());
		return "newProject.jsp";
	}

	@PostMapping("/projects/new")
	public String createProject(@Valid @ModelAttribute("project") Project project, BindingResult result, Model model,
			HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		if (result.hasErrors()) {
			model.addAttribute("loggedUser", userService.findById((Long) session.getAttribute("loggedUserId")));
			return "newProject.jsp";
		}
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		project.setUser(user);
		project.addTeamMember(user);
		projectService.createProject(project);
		return "redirect:/dashboard";
	}

	@GetMapping("/projects/{id}")
	public String viewProject(@PathVariable Long id, Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		Project project = projectService.findById(id);
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		if (project.getUser().equals(user))
			model.addAttribute("myProject", true);
		model.addAttribute("loggedUser", user);
		model.addAttribute("project", project);
		return "showProject.jsp";
	}

	@GetMapping("projects/edit/{id}")
	public String showEdit(@PathVariable Long id, Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		Project project = projectService.findById(id);
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		if (!project.getUser().equals(user))
			return "redirect:/dashboard";
		model.addAttribute("loggedUser", user);
		model.addAttribute("project", project);
		return "editProject.jsp";
	}

	@PutMapping("projects/edit/{id}")
	public String editProject(@Valid @ModelAttribute("project") Project updatedProject, BindingResult result, @PathVariable Long id,
			Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		Project project = projectService.findById(id);
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		if(result.hasErrors()) {
			model.addAttribute("loggedUser", user);
			return "editProject.jsp";
		}
		if (!project.getUser().equals(user))
			return "redirect:/dashboard";
		updatedProject.setUser(user);
		updatedProject.setTeamMembers(project.getTeamMembers());
		updatedProject.setTasks(project.getTasks());
		projectService.editProject(updatedProject);
		return "redirect:/dashboard";
	}
	
	@DeleteMapping("projects/{id}")
	public String deleteProject(@PathVariable Long id, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		Project project = projectService.findById(id);
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		if (!project.getUser().equals(user))
			return "redirect:/dashboard";
		projectService.deleteProject(project);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/projects/{id}/tasks")
	public String viewTasks(@PathVariable Long id, Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		Project project = projectService.findById(id);
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		model.addAttribute("loggedUser", user);
		model.addAttribute("project", project);
		model.addAttribute("task", new ProjectTask());
		return "taskPage.jsp";
	}
	
	@PostMapping("/projects/{id}/tasks")
	public String addTask(@Valid @ModelAttribute("task") ProjectTask newTask, BindingResult result, @PathVariable Long id,
			Model model, HttpSession session) {
		if (session.getAttribute("loggedUserId") == null)
			return "redirect:/";
		Project project = projectService.findById(id);
		User user = userService.findById((Long) session.getAttribute("loggedUserId"));
		if(result.hasErrors()) {
			model.addAttribute("loggedUser", user);
			model.addAttribute("project", project);
			return "taskPage.jsp";
		}
		newTask.setPoster(user);
		newTask.setProject(project);
		projectTaskService.createProjectTask(newTask);
		project.addTask(newTask);
		projectService.editProject(project);
		return "redirect:/projects/" + id + "/tasks";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
