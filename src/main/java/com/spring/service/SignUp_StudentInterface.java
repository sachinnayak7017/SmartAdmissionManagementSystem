package com.spring.service;

import com.spring.entity.SignUp_Student;

public interface SignUp_StudentInterface {
	
   public SignUp_Student saveStudent(SignUp_Student signup, String url);
	
	public void removeSessionMessage();
	
	public void sendEmail(SignUp_Student signup, String path);

}