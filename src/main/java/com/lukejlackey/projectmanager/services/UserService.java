package com.lukejlackey.projectmanager.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.lukejlackey.projectmanager.models.LoginUser;
import com.lukejlackey.projectmanager.models.User;
import com.lukejlackey.projectmanager.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;

	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		return null;
	}
	
	public User registerUser(User newUser, BindingResult result) {
		Optional<User> optionalUser = userRepo.findByEmail(newUser.getEmail());
		if(optionalUser.isPresent()) {
			result.rejectValue("email", "unique", "This email is already registered. Please use another.");
		}
		if(!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("confirm", "matches", "Password and Confirm Password fields must match.");
		}
		if(result.hasErrors()) return null;
		newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
		return userRepo.save(newUser);
	}
	
	public User login(LoginUser newLogin, BindingResult result) {
		Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());
		if(!potentialUser.isPresent()) {
			result.rejectValue("email","unique","There is no account registered with this email.");
			return null;
		}
		User user = potentialUser.get();
		if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
			result.rejectValue("password", "matches", "Invalid password");
		}
		if(result.hasErrors()) return null;
		return user;
	}
	
//	public User editUser(User User) {
//		return userRepo.save(User);
//	}
//	
//	public void deleteUser(User User) {
//		userRepo.deleteById(User.getId());
//		return;
//	}

}