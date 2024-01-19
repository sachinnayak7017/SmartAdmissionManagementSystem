package com.spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entity.Students_Details;
import com.spring.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable>{
	
	public User findByEmail(String email);
	
	 long count();
	 
	 
	 @Query(value = "select * from user e where e.name like %:keyword% " , nativeQuery = true)
		List<User> findBykeyword(@Param("keyword") String keyword);
	
}
