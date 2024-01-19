package com.spring.service;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.dao.StudentDetailsRepository;
import com.spring.entity.Students_Details;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class StudentDetailsService {
	
	@Autowired
	private StudentDetailsRepository strepo;
	
	
	 public long generateEnrollmentNumber() {
	        int year = Year.now().getValue();
	        long count = strepo.count() + 1;
	        return Long.parseLong(String.format("%04d%02d%04d", year, 1, count));
	    }
	
	public void addStudentDetails(Students_Details spi)
	{
		
		strepo.save(spi);
	}

	public List<Students_Details> getAllStudentsDetails()
	{
		return strepo.findAll();
		
	}
	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");

	}
	
	public Students_Details getStudentById(int s_id)
	{
		Optional<Students_Details> st = strepo.findById(s_id); 
		if(st.isPresent()) {
			return st.get();
		}
		return null;
	}
	
	public void deleteStudentDetails(Integer s_id)
	{
		strepo.deleteById(s_id);
		
	}
	
	public void generateExcel(HttpServletResponse response) throws IOException {
		 
		List<Students_Details> stinfo = strepo.findAll();
		HSSFWorkbook  workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet("Student_Details");
	 HSSFRow row  = sheet.createRow(0);
	 row.createCell(0).setCellValue("s_Id");
	 row.createCell(1).setCellValue("enrollment_no");
	 row.createCell(2).setCellValue("roll_no");
	 row.createCell(3).setCellValue("name"); 
	 row.createCell(4).setCellValue("fatherName");
	 row.createCell(5).setCellValue("motherName"); 
	 row.createCell(6).setCellValue("dob");
	 row.createCell(7).setCellValue("gender");
	 row.createCell(8).setCellValue("email");
	 row.createCell(9).setCellValue("contact_no_1"); 
	 row.createCell(10).setCellValue("contact_no_2");
	 row.createCell(11).setCellValue("address"); 
	 row.createCell(12).setCellValue("district");
	 row.createCell(13).setCellValue("state");
	 row.createCell(14).setCellValue("pincode"); 
	 row.createCell(15).setCellValue("aadhaar_no");
	 row.createCell(16).setCellValue("pan_no");
	 row.createCell(17).setCellValue("religion"); 
	 row.createCell(18).setCellValue("category");
	 row.createCell(19).setCellValue("admission_year");
	 row.createCell(20).setCellValue("admission_type"); 
	 row.createCell(21).setCellValue("department");
	 row.createCell(22).setCellValue("course");
	 row.createCell(23).setCellValue("branch");
	 row.createCell(24).setCellValue("tenth_marks");
	 row.createCell(25).setCellValue("tenth_passing_year");
	 row.createCell(26).setCellValue("tenth_board"); 
	 row.createCell(27).setCellValue("twelth_marks");
	 row.createCell(28).setCellValue("twelth_passing_year"); 
	 row.createCell(29).setCellValue("twelth_board");
	 row.createCell(30).setCellValue("twelth_stream");
	 row.createCell(31).setCellValue("diploma_course");
	 row.createCell(32).setCellValue("diploma_branch"); 
	 row.createCell(33).setCellValue("diploma_marks");
	 row.createCell(34).setCellValue("diploma_passing_year"); 
	 row.createCell(35).setCellValue("graduation_university");
	 row.createCell(36).setCellValue("graduation_course");
	 row.createCell(37).setCellValue("graduation_branch"); 
	 row.createCell(38).setCellValue("graduation_marks");
	 row.createCell(39).setCellValue("graduation_passing_year");
	 row.createCell(40).setCellValue("tc"); 
	 row.createCell(41).setCellValue("cc");
	 row.createCell(42).setCellValue("aadhaar_card");
	 row.createCell(43).setCellValue("tenth_marksheet"); 
	 row.createCell(44).setCellValue("twelth_marksheet");
	 row.createCell(45).setCellValue("diploma");
	 row.createCell(46).setCellValue("graduation_degree");
	 
	 int dataRowIndex = 1;
	 for( Students_Details studentinfo :stinfo) {
		HSSFRow dataRow = sheet.createRow(dataRowIndex);
		dataRow.createCell(0).setCellValue(studentinfo.getS_id());
		dataRow.createCell(1).setCellValue(studentinfo.getEnrollment_no());
		dataRow.createCell(2).setCellValue(studentinfo.getRoll_no());
		dataRow.createCell(3).setCellValue(studentinfo.getName());
		dataRow.createCell(4).setCellValue(studentinfo.getFatherName());
		dataRow.createCell(5).setCellValue(studentinfo.getMotherName());
		dataRow.createCell(6).setCellValue(studentinfo.getDob());
		dataRow.createCell(7).setCellValue(studentinfo.getGender());
		dataRow.createCell(8).setCellValue(studentinfo.getEmail());
		dataRow.createCell(9).setCellValue(studentinfo.getContact_no_1());
		dataRow.createCell(10).setCellValue(studentinfo.getContact_no_2());
		dataRow.createCell(11).setCellValue(studentinfo.getAddress());
		dataRow.createCell(12).setCellValue(studentinfo.getDistrict());
		dataRow.createCell(13).setCellValue(studentinfo.getState());
		dataRow.createCell(14).setCellValue(studentinfo.getPincode());
		dataRow.createCell(15).setCellValue(studentinfo.getAadhaar_no());
		dataRow.createCell(16).setCellValue(studentinfo.getPan_no());
		dataRow.createCell(17).setCellValue(studentinfo.getReligion());
		dataRow.createCell(18).setCellValue(studentinfo.getCategory());
		dataRow.createCell(19).setCellValue(studentinfo.getAdmission_year());
		dataRow.createCell(20).setCellValue(studentinfo.getAdmission_type());
		dataRow.createCell(21).setCellValue(studentinfo.getDepartment());
		dataRow.createCell(22).setCellValue(studentinfo.getCourse());
		dataRow.createCell(23).setCellValue(studentinfo.getBranch());
		dataRow.createCell(24).setCellValue(studentinfo.getTenth_marks());
		dataRow.createCell(25).setCellValue(studentinfo.getTenth_passing_year());
		dataRow.createCell(26).setCellValue(studentinfo.getTenth_board());
		dataRow.createCell(27).setCellValue(studentinfo.getTwelth_marks());
		dataRow.createCell(28).setCellValue(studentinfo.getTwelth_passing_year());
		dataRow.createCell(29).setCellValue(studentinfo.getTwelth_board());
		dataRow.createCell(30).setCellValue(studentinfo.getTwelth_stream());
		dataRow.createCell(31).setCellValue(studentinfo.getDiploma_course());
		dataRow.createCell(32).setCellValue(studentinfo.getDiploma_branch());
		dataRow.createCell(33).setCellValue(studentinfo.getDiploma_marks());
		dataRow.createCell(34).setCellValue(studentinfo.getDiploma_passing_year());
		dataRow.createCell(35).setCellValue(studentinfo.getGraduation_university());
		dataRow.createCell(36).setCellValue(studentinfo.getGraduation_course());
		dataRow.createCell(37).setCellValue(studentinfo.getGraduation_branch());
		dataRow.createCell(38).setCellValue(studentinfo.getGraduation_marks());
		dataRow.createCell(39).setCellValue(studentinfo.getGraduation_passing_year());
		dataRow.createCell(40).setCellValue(studentinfo.getTc());
		dataRow.createCell(41).setCellValue(studentinfo.getCc());
		dataRow.createCell(42).setCellValue(studentinfo.getAadhaar_card());
		dataRow.createCell(43).setCellValue(studentinfo.getTenth_marksheet());
		dataRow.createCell(44).setCellValue(studentinfo.getTwelth_marksheet());
		dataRow.createCell(45).setCellValue(studentinfo.getDiploma());
		dataRow.createCell(46).setCellValue(studentinfo.getGraduation_degree());
		dataRowIndex ++;
	 }
	
	 ServletOutputStream ops = response.getOutputStream();
	 workbook.write(ops);
	 workbook.close();
	 ops.close();
		
		
	}
	
	public List<Students_Details> findByKeyword(String keyword){
		return strepo.findBykeyword(keyword);
	}

	/// upload excel file
	public void save(Students_Details sinfo) {
		// TODO Auto-generated method stub

	}

}
