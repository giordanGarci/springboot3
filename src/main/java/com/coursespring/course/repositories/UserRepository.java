package com.coursespring.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coursespring.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
