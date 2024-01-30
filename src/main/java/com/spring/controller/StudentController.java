/*
 * package com.spring.controller;
 * 
 * import java.security.Principal; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import com.spring.dao.SignUpStudentRepo; import
 * com.spring.dao.StudentEducationalRepository; import
 * com.spring.dao.StudentDetailsRepository; import
 * com.spring.dao.UserRepository; import com.spring.entity.SignUp_Student;
 * import com.spring.entity.Student_E_DETAILS; import
 * com.spring.entity.Students_Details; import com.spring.entity.User; import
 * com.spring.service.StudentEductionalService; import
 * com.spring.service.StudentDetailsService;
 * 
 * import jakarta.servlet.http.HttpSession;
 * 
 * @Controller
 * 
 * @RequestMapping("/student") public class StudentController {
 * 
 * @Autowired private UserRepository userRepo;
 * 
 * @Autowired SignUpStudentRepo signupRepo;
 * 
 * @Autowired private StudentDetailsService spservice;
 * 
 * @Autowired private StudentDetailsRepository sprepo;
 * 
 * @Autowired private StudentEducationalRepository serepo;
 * 
 * @Autowired private StudentEductionalService seservice;
 * 
 * @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
 * 
 * @ModelAttribute public void commonStudent(Principal p, Model m) { if (p !=
 * null) { String email = p.getName(); SignUp_Student signup_student =
 * signupRepo.findByEmail(email); m.addAttribute("signup_student",
 * signup_student); }
 * 
 * }
 * 
 * @GetMapping("/home") public String home() { return
 * "student/student_dashboard"; }
 * 
 * @GetMapping("/profile") public String profile() { return "student/profile"; }
 * 
 * @GetMapping("/student_dashboard") public String userDashboard(Model m) {
 * m.addAttribute("title", "Home"); m.addAttribute("stpinfo", new
 * Students_Details());
 * 
 * return "student/student_dashboard"; }
 * 
 * @GetMapping("/add_student_personal") public String
 * openAddStudentPersonal(Model m) {
 * 
 * m.addAttribute("title", "Add Student"); m.addAttribute("stpinfo", new
 * Students_Details());
 * 
 * return "student/add_student_personal"; }
 * 
 * @GetMapping("/add_student_education") public String
 * openAddStudentEducation(Model m) { m.addAttribute("title", "Add Student");
 * m.addAttribute("steinfo", new Student_E_DETAILS());
 * 
 * return "student/add_student_edu"; }
 * 
 * 
 * @PostMapping("/save_student_personal") public String
 * saveStudentPersonal(@ModelAttribute Students_Details studentpersonal,
 * HttpSession session) {
 * 
 * System.out.println(studentpersonal);
 * spservice.addStudentPersonal(studentpersonal);
 * 
 * session.setAttribute("msg", "Your personal details added successfully !");
 * 
 * return "student/add_student_edu"; }
 * 
 * @PostMapping("/save_student_educational") public String
 * saveStudentEducational(@ModelAttribute Student_E_DETAILS studenteducational,
 * HttpSession session) {
 * 
 * System.out.println(studenteducational);
 * seservice.addStudentEducational(studenteducational);
 * 
 * session.setAttribute("msg", "Your educational details added successfully !");
 * 
 * return "student/profile"; }
 * 
 * 
 * }
 * 
 */

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dao.SignUpStudentRepo;
import com.spring.dao.StudentDetailsRepository;
import com.spring.entity.SignUp_Student;
import com.spring.entity.Students_Details;
import com.spring.entity.User;
import com.spring.service.StudentDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentDetailsService stservice;

	@Autowired
	private SignUpStudentRepo signupRepo;
	
	@Autowired
	private StudentDetailsRepository strepo;

	@GetMapping("/student_dashboard")
	public String studentDashboard(Model model, Principal principal) {
		
		long totalCount1 = strepo.count();
        model.addAttribute("totalCount1", totalCount1);
        
        long totalCount2 = signupRepo.count();
        model.addAttribute("totalCount2", totalCount2);
        
		if (principal != null) {
			String email = principal.getName();
			Optional<SignUp_Student> studentOptional = signupRepo.findByEmail(email);

			if (studentOptional.isPresent()) {
				SignUp_Student student = studentOptional.get();
				model.addAttribute("student", student);
			} else {

				return "redirect:/error";
			}
		}
	
		return "student/student_dashboard";
	}

	@GetMapping("/profile")
	public String profile() {
		return "student/profile";
	}

	@GetMapping("/add_student_details")
	public String openAddStudentPersonal(Model m) {

		m.addAttribute("title", "Add Student");
		m.addAttribute("stpinfo", new Students_Details());
		 long enrollmentNumber = stservice.generateEnrollmentNumber();

	        // Create a new student with the generated enrollment number
	        Students_Details student = new Students_Details();
	        student.setEnrollment_no(enrollmentNumber);

	        // Add the student to the model
	        m.addAttribute("student", student);
	     
		return "student/add_student_details";
	}
	
	@PostMapping("/save_student_details")
	public String saveStudentDetails(@ModelAttribute Students_Details studentdetails, HttpSession session,@RequestParam("file") MultipartFile file) {

		try {
			if (!file.isEmpty()) {

				long MAX_FILE_SIZE = 10 * 1024 * 1024;
				if (file.getSize() <= MAX_FILE_SIZE) {

					byte[] imageData = file.getBytes();
					studentdetails.setImageData(imageData);
					stservice.addStudentDetails(studentdetails);
					session.setAttribute("msg", "student Added Sucessfully..");
				} else {
					session.setAttribute("msg", "student Not Added Sucessfully..");
				}

			}
		} catch (IOException e1) {
			// Handle the exception
			session.setAttribute("msg", "student Not Added Sucessfully..");
		}
		return "redirect:/student/add_student_details";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/login";
	}
}