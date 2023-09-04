package com.coursespring.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.coursespring.course.entities.User;
import com.coursespring.course.repositories.UserRepository;
import com.coursespring.course.resources.exceptions.DatabaseException;
import com.coursespring.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User user) {
		return userRepository.save(user);
	}

	public void delete(Long id){
	    try {
	        if(!userRepository.existsById(id)) throw new ResourceNotFoundException(id);
	        userRepository.deleteById(id);
	    }catch (ResourceNotFoundException e){
	        throw new ResourceNotFoundException(id);
	    }catch(DataIntegrityViolationException e) {
	    	throw new DatabaseException(e.getMessage());
	    }
	}

	public User update(Long id, User user) {
		User obj = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário com Id: " + id + " não encontrado."));

		updateData(obj, user);
		return userRepository.save(obj);
	}

	private void updateData(User entity, User user) {
		entity.setName(user.getName());
		entity.setEmail(user.getEmail());
		entity.setPhone(user.getPhone());
	}
}
