

package com.spring.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.spring.dao.SignUpStudentRepo;
import com.spring.dao.StudentDetailsRepository;
import com.spring.dao.UserRepository;
import com.spring.entity.SignUp_Student;
import com.spring.entity.Students_Details;
import com.spring.entity.User;
import com.spring.service.SignUp_StudentService;
import com.spring.service.StudentDetailsService;
import com.spring.service.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SignUpStudentRepo signupRepo;

	@Autowired
	private SignUp_StudentService signupservice;

	@Autowired
	private UserServiceImp userService;

	@Autowired
	private StudentDetailsService stservice;

	@Autowired
	private StudentDetailsRepository strepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

////show image in html table
	@GetMapping("/view_users/{id}")
	public ResponseEntity<byte[]> getEmployeeImage(@PathVariable int id) {
		User user = userService.getUserById(id);

		if (user != null && user.getImageData() != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type as needed
			System.out.println("image upload");
			return new ResponseEntity<>(user.getImageData(), headers, HttpStatus.OK);
		}
		System.out.println("image not uplode");
		// Return a placeholder image or an error image if needed
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/home")
	public String home() {
		return "admin_dashboard";
	}

	@GetMapping("/profile")
	public String adminProfile() {
		return "user/profile";
	}

	@GetMapping("/fileupoad")
	public String formate() {
		return "user/fileupoad";
	}

	@GetMapping("/user_dashboard")
	public String userDashboard(Model m) {
		m.addAttribute("title", "Home");
		m.addAttribute("stpinfo", new Students_Details());
		
		long totalCount = userRepo.count();
        m.addAttribute("totalCount", totalCount);
        
        long totalCount1 = strepo.count();
        m.addAttribute("totalCount1", totalCount1);
        
        long totalCount2 = signupRepo.count();
        m.addAttribute("totalCount2", totalCount2);

		return "user/user_dashboard";
	}

	@GetMapping("/signup_student")
	public String signupStudent(Model m) {
		m.addAttribute("title", "SignUp Student");

		return "user/signup_student";
	}

	@GetMapping("/add_student_details")
	public String openAddStudentDetails(Model m) {
		m.addAttribute("title", "Add Student");
		m.addAttribute("stinfo", new Students_Details());
		 long enrollmentNumber = stservice.generateEnrollmentNumber();

	        // Create a new student with the generated enrollment number
	        Students_Details student = new Students_Details();
	        student.setEnrollment_no(enrollmentNumber);

	        // Add the student to the model
	        m.addAttribute("student", student);

		return "user/add_student_details";
	}

	@GetMapping("/view_students_details")
	public String openViewStudentDetails(Model m) {

		List<Students_Details> stinfo = stservice.getAllStudentsDetails();

		m.addAttribute("title", "View Students");
		m.addAttribute("stinfo", stinfo);

		return "user/view_students_details";
	}
	//show image in html table
		@GetMapping("/view_student/{id}")
		public ResponseEntity<byte[]> getstudentImage(@PathVariable int id) {
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

	@GetMapping("/update_students_details/{s_id}")
	public String openUpdateStudentPersonal(@PathVariable int s_id, Model m) {

		m.addAttribute("title", "Update Students");
		Students_Details stp = stservice.getStudentById(s_id);
		m.addAttribute("stinfo", stp);

		return "user/update_students_details";
	}

	@PostMapping("/update/{s_id}")
	public String UpdateStudent(@ModelAttribute Students_Details stp, HttpSession session, @RequestParam("file") MultipartFile file) {

		try {
			if (!file.isEmpty()) {

				long MAX_FILE_SIZE = 10 * 1024 * 1024;
				if (file.getSize() <= MAX_FILE_SIZE) {

					byte[] imageData = file.getBytes();
					stp.setImageData(imageData);
					stservice.addStudentDetails(stp);
					session.setAttribute("msg", "student Added Sucessfully..");
				} else {
					session.setAttribute("msg", "student Not Added Sucessfully..");
				}

			}
		} catch (IOException e1) {
			// Handle the exception
			session.setAttribute("msg", "student Not Added Sucessfully..");
		}
		return "user/user_dashboard";
	}
	  @PostMapping("/deletes")
	    public String deleteStudents(@RequestParam(name = "studentIds", required = false) List<Integer> studentIds,
	                                 @RequestParam(name = "deleteAll", required = false) boolean deleteAll) {
	        if (deleteAll) {
	            // Delete all students
	            strepo.deleteAll();
	        } else if (studentIds != null && !studentIds.isEmpty()) {
	            // Delete selected students
	            strepo.deleteAllById(studentIds);
	        }
	        return "user/user_dashboard";
	    }

	@GetMapping("/delete/{s_id}")
	public String deleteStudent(@PathVariable int s_id, HttpSession session) {

		stservice.deleteStudentDetails(s_id);
		session.setAttribute("msg", "Student's details deleted successsfully...");

		return "user/user_dashboard";
	}

	// for image
//	ALTER TABLE EMP_SYSTEM
//	MODIFY COLUMN image_data LONGBLOB; --
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
		return "user/user_dashboard";
	}

	@GetMapping("/exportStudentsDetails")
	public void generateExcelReport(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=Students_Details.xls";
		response.setHeader(headerKey, headerValue);

		stservice.generateExcel(response);
	}

	@RequestMapping("/show_student_details/{s_id}")
	public String openShowStudentPersonal(@PathVariable("s_id") Integer s_id, Model m) {

		m.addAttribute("title", "Show Student");
		m.addAttribute("stpinfo", new Students_Details());

		Optional<Students_Details> stOptional = this.strepo.findById(s_id);
		Students_Details stinfo = stOptional.get();

		m.addAttribute("stinfo", stinfo);

		return "user/show_student_details";
	}

	@GetMapping("/change_student_password")
	public String userPassword(Model m, HttpSession session) {
		m.addAttribute("title", "Change Student Password");

		return "user/change_student_password";
	}

	@GetMapping("/change_user_password")
	public String adminPassword(Model m, HttpSession session) {
		m.addAttribute("title", "Change User Password");

		return "user/change_user_password";
	}

	@PostMapping("/change-user-password")
	public String changeUserPassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal p, HttpSession session) {

		session.setAttribute("msg", "Your password have been changed successfully !");

		System.out.println("OLD PASSWORD " + oldPassword);
		System.out.println("NEW PASSWORD " + newPassword);

		String userName = p.getName();
		User currentUser = this.userRepo.findByEmail(userName);
		System.out.println(currentUser.getPassword());

		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepo.save(currentUser);

		} else {

		}

		return "user/user_dashboard";
	}

	/* Search coding */
//
	@GetMapping("/filterper")
	public String FilterStudentDetails(Model m, String keyword) {
		List<Students_Details> stinfo = stservice.getAllStudentsDetails();

		if (keyword != null) {
			m.addAttribute("stinfo", stservice.findByKeyword(keyword));
		} else {
			m.addAttribute("stinfo", stinfo);
		}

		return "user/view_students_details";
	}
	
	
	
	@RequestMapping("/admission_letter/{s_id}")
	public String generateAdmissionLetter(@PathVariable("s_id") Integer s_id, Model m) {

		m.addAttribute("title", "Show Student");
		m.addAttribute("stpinfo", new Students_Details());

		Optional<Students_Details> stOptional = this.strepo.findById(s_id);
		Students_Details stinfo = stOptional.get();

		m.addAttribute("stinfo", stinfo);

		return "user/admission_letter";
	}

	@RequestMapping("/admission_slip/{s_id}")
	public String generateAdmissionSlip(@PathVariable("s_id") Integer s_id, Model m) {

		m.addAttribute("title", "Show Student");
		m.addAttribute("stpinfo", new Students_Details());

		Optional<Students_Details> stOptional = this.strepo.findById(s_id);
		Students_Details stinfo = stOptional.get();

		m.addAttribute("stinfo", stinfo);

		return "user/admission_slip";
	}

	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute SignUp_Student s_student, HttpSession session,
			HttpServletRequest request) {

		String url = request.getRequestURI().toString();

		url = url.replace(request.getServletPath(), "");

		SignUp_Student signup = signupservice.saveStudent(s_student, url);

		if (signup != null) {
			session.setAttribute("msg",
					"Student Registered successfully ! And Password has been sent on registered email !"); //
			System.out.println("save success!");
		} else {
			session.setAttribute("msg", "Something wrong on server!");
			System.out.println("error!");
		}

		return "user/signup_student";
	}
////////////////import data from excel to data base

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {

			if (file.isEmpty()) {
				return "redirect:/?error=Empty file";
			}
			DataFormatter dataFormatter = new DataFormatter();////
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();

			if (rowIterator.hasNext()) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Students_Details sinfo = new Students_Details();

				Cell enrollment_noCell = row.getCell(0);
				if (enrollment_noCell.getCellType() == CellType.STRING) {

					try {
						sinfo.setEnrollment_no(Integer.parseInt(enrollment_noCell.getStringCellValue()));
					} catch (NumberFormatException e) {

						sinfo.setEnrollment_no(0);
					}
				} else if (enrollment_noCell.getCellType() == CellType.NUMERIC) {

					sinfo.setEnrollment_no((int) enrollment_noCell.getNumericCellValue());
				} else {

					sinfo.setEnrollment_no(0);
				}
				Cell roll_noCell = row.getCell(1);
				if (roll_noCell.getCellType() == CellType.STRING) {

					try {
						sinfo.setRoll_no(Integer.parseInt(roll_noCell.getStringCellValue()));
					} catch (NumberFormatException e) {

						sinfo.setRoll_no(0);
					}
				} else if (roll_noCell.getCellType() == CellType.NUMERIC) {

					sinfo.setRoll_no((int) roll_noCell.getNumericCellValue());
				} else {

					sinfo.setRoll_no(0);
				}
				sinfo.setName(row.getCell(2).getStringCellValue());
				sinfo.setFatherName(row.getCell(3).getStringCellValue());
				sinfo.setMotherName(row.getCell(4).getStringCellValue());
				sinfo.setDob(dataFormatter.formatCellValue(row.getCell(5)));
				sinfo.setGender(row.getCell(6).getStringCellValue());
				sinfo.setEmail(row.getCell(7).getStringCellValue());
				sinfo.setContact_no_1(dataFormatter.formatCellValue(row.getCell(8)));
				sinfo.setContact_no_2(dataFormatter.formatCellValue(row.getCell(9)));
				sinfo.setAddress(row.getCell(10).getStringCellValue());
				sinfo.setDistrict(row.getCell(11).getStringCellValue());
				sinfo.setState(row.getCell(12).getStringCellValue());
				sinfo.setPincode(dataFormatter.formatCellValue(row.getCell(13)));
				sinfo.setAadhaar_no(dataFormatter.formatCellValue(row.getCell(14)));
				sinfo.setPan_no(dataFormatter.formatCellValue(row.getCell(15)));
				sinfo.setReligion(row.getCell(16).getStringCellValue());
				sinfo.setCategory(row.getCell(17).getStringCellValue());
				sinfo.setAdmission_year(dataFormatter.formatCellValue(row.getCell(18)));
				sinfo.setAdmission_type(row.getCell(19).getStringCellValue());
				sinfo.setDepartment(row.getCell(20).getStringCellValue());
				sinfo.setCourse(row.getCell(21).getStringCellValue());
				sinfo.setBranch(row.getCell(22).getStringCellValue());
				Cell tenth_marksCell = row.getCell(23);
				if (tenth_marksCell.getCellType() == CellType.STRING) {

					try {
						sinfo.setTenth_marks(Integer.parseInt(tenth_marksCell.getStringCellValue()));
					} catch (NumberFormatException e) {

						sinfo.setTenth_marks(0);
					}
				} else if (tenth_marksCell.getCellType() == CellType.NUMERIC) {

					sinfo.setTenth_marks((int) tenth_marksCell.getNumericCellValue());
				} else {

					sinfo.setTenth_marks(0);
				}
				Cell tenth_passing_yearCell = row.getCell(24);
				if (tenth_passing_yearCell.getCellType() == CellType.STRING) {

					try {
						sinfo.setTenth_passing_year(Integer.parseInt(tenth_passing_yearCell.getStringCellValue()));
					} catch (NumberFormatException e) {

						sinfo.setTenth_passing_year(0);
					}
				} else if (tenth_passing_yearCell.getCellType() == CellType.NUMERIC) {

					sinfo.setTenth_passing_year((int) tenth_passing_yearCell.getNumericCellValue());
				} else {

					sinfo.setTenth_passing_year(0);
				}
				sinfo.setTenth_board(row.getCell(25).getStringCellValue());
				Cell twelth_marksCell = row.getCell(26);
				if (twelth_marksCell.getCellType() == CellType.STRING) {

					try {
						sinfo.setTwelth_marks(Integer.parseInt(twelth_marksCell.getStringCellValue()));
					} catch (NumberFormatException e) {

						sinfo.setTwelth_marks(0);
					}
				} else if (twelth_marksCell.getCellType() == CellType.NUMERIC) {

					sinfo.setTwelth_marks((int) twelth_marksCell.getNumericCellValue());
				} else {

					sinfo.setTwelth_marks(0);
				}
				Cell twelth_passing_yearCell = row.getCell(27);
				if (twelth_passing_yearCell.getCellType() == CellType.STRING) {

					try {
						sinfo.setTwelth_passing_year(Integer.parseInt(twelth_passing_yearCell.getStringCellValue()));
					} catch (NumberFormatException e) {

						sinfo.setTwelth_passing_year(0);
					}
				} else if (twelth_passing_yearCell.getCellType() == CellType.NUMERIC) {

					sinfo.setTwelth_passing_year((int) twelth_passing_yearCell.getNumericCellValue());
				} else {

					sinfo.setTwelth_passing_year(0);
				}
				sinfo.setTwelth_board(row.getCell(28).getStringCellValue());
				sinfo.setTwelth_stream(row.getCell(29).getStringCellValue());
				sinfo.setDiploma_course(row.getCell(30).getStringCellValue());
				sinfo.setDiploma_branch(row.getCell(31).getStringCellValue());
				sinfo.setDiploma_marks(dataFormatter.formatCellValue(row.getCell(32)));
				sinfo.setDiploma_passing_year(dataFormatter.formatCellValue(row.getCell(33)));
				sinfo.setGraduation_university(row.getCell(34).getStringCellValue());
				sinfo.setGraduation_course(row.getCell(35).getStringCellValue());
				sinfo.setGraduation_branch(row.getCell(36).getStringCellValue());
				sinfo.setGraduation_marks(dataFormatter.formatCellValue(row.getCell(37)));
				sinfo.setGraduation_passing_year(dataFormatter.formatCellValue(row.getCell(38)));
				sinfo.setTc(row.getCell(39).getStringCellValue());
				sinfo.setCc(row.getCell(40).getStringCellValue());
				sinfo.setAadhaar_card(row.getCell(41).getStringCellValue());
				sinfo.setTenth_marksheet(row.getCell(42).getStringCellValue());
				sinfo.setTwelth_marksheet(row.getCell(43).getStringCellValue());
				sinfo.setDiploma(row.getCell(44).getStringCellValue());
				sinfo.setGraduation_degree(row.getCell(45).getStringCellValue());

				strepo.save(sinfo);
				System.out.println(sinfo);
			}
			workbook.close();
			return "redirect:/?success";

		} catch (Exception e) {
			return "redirect:/?error=Error uploading file: " + e.getMessage();
		}
	}
///////////////////

/// generate formeta excel file
	@GetMapping("/generate-template")
	public void generateTemplate(HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=Students_Details.xls");

		Workbook workbook = new XSSFWorkbook();
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Data Template");

// Create header row
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("enrollment_no");
		header.createCell(1).setCellValue("roll_no");
		header.createCell(2).setCellValue("name");
		header.createCell(3).setCellValue("fatherName");
		header.createCell(4).setCellValue("motherName");
		header.createCell(5).setCellValue("dob");
		header.createCell(6).setCellValue("gender");
		header.createCell(7).setCellValue("email");
		header.createCell(8).setCellValue("contact_no_1");
		header.createCell(9).setCellValue("contact_no_2");
		header.createCell(10).setCellValue("address");
		header.createCell(11).setCellValue("district");
		header.createCell(12).setCellValue("state");
		header.createCell(13).setCellValue("pincode");
		header.createCell(14).setCellValue("aadhaar_no");
		header.createCell(15).setCellValue("pan_no");
		header.createCell(16).setCellValue("religion");
		header.createCell(17).setCellValue("category");
		header.createCell(18).setCellValue("admission_year");
		header.createCell(19).setCellValue("asmission_type");
		header.createCell(20).setCellValue("department");
		header.createCell(21).setCellValue("course");
		header.createCell(22).setCellValue("branch");
		header.createCell(23).setCellValue("tenth_marks");
		header.createCell(24).setCellValue("tenth_passing_year");
		header.createCell(25).setCellValue("tenth_board");
		header.createCell(26).setCellValue("twelth_marks");
		header.createCell(27).setCellValue("twelth_passing_year");
		header.createCell(28).setCellValue("twelth_board");
		header.createCell(29).setCellValue("twelth_stream");
		header.createCell(30).setCellValue("diploma_course");
		header.createCell(31).setCellValue("diploma_branch");
		header.createCell(32).setCellValue("diploma_marks");
		header.createCell(33).setCellValue("diploma_passing_year");
		header.createCell(34).setCellValue("graduation_university");
		header.createCell(35).setCellValue("graduation_course");
		header.createCell(36).setCellValue("graduation_branch");
		header.createCell(37).setCellValue("graduation_marks");
		header.createCell(38).setCellValue("graduation_passing_year");
		header.createCell(39).setCellValue("tc");
		header.createCell(40).setCellValue("cc");
		header.createCell(41).setCellValue("aadhaar_card");
		header.createCell(42).setCellValue("tenth_marksheet");
		header.createCell(43).setCellValue("twelth_marksheet");
		header.createCell(44).setCellValue("diploma");
		header.createCell(45).setCellValue("graduation_degree");

		workbook.write(response.getOutputStream());
		workbook.close();
	}

}
