package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.dao.SignUpStudentRepo;
import com.spring.entity.SignUp_Student;
import jakarta.servlet.http.HttpSession;

@Service
public class SignUp_StudentService implements SignUp_StudentInterface{

	@Autowired
	private SignUpStudentRepo signupRepo;


	@Override
	public SignUp_Student saveStudent(SignUp_Student signup , String url) {

		String password = (signup.getPassword());
		signup.setPassword(password);
		signup.setRole("ROLE_STUDENT");
		
		SignUp_Student newstudent = signupRepo.save(signup);
		
		return newstudent;
	}

	@Override
	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");

	}

	@Override
	public void sendEmail(SignUp_Student signup, String path) {
		// TODO Auto-generated method stub
		
	}
	
}
