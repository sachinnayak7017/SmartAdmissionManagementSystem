package com.spring.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.spring.entity.Students_Details;
import com.spring.entity.User;

@Repository
/*public interface StudentPersonalRepository extends JpaRepository<Student_P_INFO, Integer>{
*/
public interface StudentDetailsRepository extends JpaRepository<Students_Details, Serializable>{

	/* this is for search bar in view table  */
	
	@Query(value = "select * from students_details e where " +
	        "e.name like %:keyword% " +
	        "or e.father_name like %:keyword% " +
	        "or e.mother_name like %:keyword% " +
	        "or e.course like %:keyword% " +
	        "or e.branch like %:keyword% " , nativeQuery = true)
	List<Students_Details> findBykeyword(@Param("keyword") String keyword);
	
	long count();
	

}
