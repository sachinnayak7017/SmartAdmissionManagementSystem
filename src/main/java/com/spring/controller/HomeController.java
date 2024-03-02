package com.spring.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dao.SignUpStudentRepo;
import com.spring.dao.UserRepository;
import com.spring.entity.SignUp_Student;
import com.spring.entity.User;
import com.spring.service.SignUp_StudentInterface;
import com.spring.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	SignUpStudentRepo signupRepo;
	@Autowired 
	private SignUp_StudentInterface studentService;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String mainIndex() {

		return "index";

	}
	
	@GetMapping("/course")
	public String course() {

		return "course";
	}

	@GetMapping("/login")
	public String adminIndex() {

		return "user-sign-up";
	}


	

	

	@GetMapping("/studentlogin")
	public String studentIndex() {

		return "student-sign-up";

	}

	@GetMapping("/student_signin")
	public String studentSignin() {

		return "student_login";

	}
	
	@GetMapping("/student_register")
	public String studentRegister() {

		return "student_register";

	}

	

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, HttpServletRequest request,@RequestParam("file") MultipartFile file) {

		String url = request.getRequestURI().toString();

		url = url.replace(request.getServletPath(), "");
		try {
			if (!file.isEmpty()) {

				long MAX_FILE_SIZE = 10 * 1024 * 1024;
				if (file.getSize() <= MAX_FILE_SIZE) {

					byte[] imageData = file.getBytes();
					user.setImageData(imageData);
				}
			}
		User u = userService.saveUser(user, url);

		if (u != null) {
			session.setAttribute("msg", "Register successfully!"); //
			
		} else {
			session.setAttribute("msg", "Something wrong on server!");
			
		}
	} catch (IOException e1) {
		// Handle the exception
	}
		return "user-sign-up";
	}
	
	
	@PostMapping("/saveStudent")
	public String savestudent(@ModelAttribute  SignUp_Student student, HttpSession session, HttpServletRequest request) {

		String url = request.getRequestURI().toString();

		url = url.replace(request.getServletPath(), "");
		
		SignUp_Student s =studentService.saveStudent(student, url);

		if (s != null) {
			session.setAttribute("msg", "Register successfully!"); //
			System.out.println("save success!");
		} else {
			session.setAttribute("msg", "Something wrong on server!");
			System.out.println("error!");
		}
	
		return "student-sign-up";
	}
	
	

	@PostMapping("/studentlogin")
	public String studentLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes, Model model) {
	    Optional<SignUp_Student> studentOptional = signupRepo.findByEmail(email);

	    if (studentOptional.isPresent()) {
	        SignUp_Student student = studentOptional.get();

	        if (student.getPassword().equals(password)) {
	            // Successful login
	            redirectAttributes.addAttribute("email", email); // Add email as a redirect attribute
	            return "redirect:/student/student_dashboard";
	        } else {
	            // Password does not match
	            model.addAttribute("error", "Invalid password");
	            return "student_login";
	        }
	    } else {
	        // No student found with the given email
	        model.addAttribute("error", "No account found with this email");
	        return "student_login";
	    }
	}
}
