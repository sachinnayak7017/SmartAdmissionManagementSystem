package com.spring.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.SignUp_Student;


public interface SignUpStudentRepo extends JpaRepository<SignUp_Student, Integer>{
	Optional<SignUp_Student> findByEmail(String email);
	
	long count();
}