

package com.spring.controller;

import java.io.IOException;
import java.security.Principal;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


////////////// change  the code  ///////////////////////////
////show image in html table
	@GetMapping("/student_dashboard/{id}")
	public ResponseEntity<byte[]> getStudentImage(@PathVariable int id) {
		Students_Details student = stservice.getStudentById(id);

		if (student != null && student.getImageData() != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type as needed
			System.out.println("image upload");
			return new ResponseEntity<>(student.getImageData(), headers, HttpStatus.OK);
		}
		System.out.println("image not uplode");
		// Return a placeholder image or an error image if needed
		return ResponseEntity.notFound().build();
	}
	
	
	@GetMapping("/student_dashboard")
	public String studentDashboard(@RequestParam(required = false) String email, Model model) {
	    if (email != null) {
	
	        Optional<SignUp_Student> studentOptional = signupRepo.findByEmail(email);
	        Optional<Students_Details> studentDetails = strepo.findByEmail(email);
	      if(studentOptional.isPresent() && studentDetails.isPresent() ) {
	        if (studentOptional.isPresent()) {
	            SignUp_Student student = studentOptional.get();
	            model.addAttribute("student", student);
	          
	        } else {
	            return "redirect:/error1";
	        }
	        
	        if (studentDetails.isPresent()) {
	            Students_Details studentinfo = studentDetails.get();
	            model.addAttribute("stpinfo", studentinfo);
	          
	        } else {
	            return "redirect:/error2";
	        }
	        }
	      else {
	    	  if (studentOptional.isPresent()) {
		            SignUp_Student student = studentOptional.get();
		            model.addAttribute("student", student);
		          
		        } else {
		            return "redirect:/error1";
		        }
	    	  
	      }
	      
	    }

	    return "student/student_dashboard";
	}


	
	@RequestMapping("/Details/{email}")
	public String generateAdmissionSlip(@PathVariable("email") String email, Model m) {
		  if (email != null) {
			  Optional<Students_Details> studentDetails = strepo.findByEmail(email);
		
			  if (studentDetails.isPresent()) {
		            Students_Details studentinfo = studentDetails.get();
		            m.addAttribute("stpinfo", studentinfo);
		            
		          
		        } else {
		            return "student/error";
		        }
		  }
		return "student/Details";
	}

	
	
	@GetMapping("/profile")
	public String profile( Model model) {
		
		    long totalCount1 = strepo.count();
		    model.addAttribute("totalCount1", totalCount1);

		    long totalCount2 = signupRepo.count();
		    model.addAttribute("totalCount2", totalCount2);
		
		    return "student/profile";
	}


/////////////////////////////////////////////////////change in this area
	
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