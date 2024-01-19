package com.spring.entity;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="STUDENTS_DETAILS")
public class Students_Details {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int s_id;
	private long enrollment_no;
	private long roll_no;
	private String name;
	private String fatherName;
	private String motherName;
	private String dob;
	private String gender; 
	private String email;
	private String contact_no_1;
	private String contact_no_2;
	private String address;
	private String district;
	private String state;
	private String pincode; 
	private String aadhaar_no;
	private String pan_no;
	private String religion;
	private String category;
	private String admission_year;
	private String admission_type;
	private String department;
	private String course;
	private String branch;
	
	private long tenth_marks;
	private long tenth_passing_year;
	private String tenth_board;
	
	private long twelth_marks;
	private long twelth_passing_year;
	private String twelth_board;
	private String twelth_stream;
	
	private String diploma_course;
	private String diploma_branch;
	private String diploma_marks;
	private String diploma_passing_year;
	
	private String graduation_university;
	private String graduation_course;
	private String graduation_branch;
	private String graduation_marks;
	private String graduation_passing_year;
	
	
	private String tc;
	private String cc;
	private String aadhaar_card;
	private String tenth_marksheet;
	private String twelth_marksheet;
	private String diploma;
	private String graduation_degree;
	 @Lob 
	    @Column(name = "image_data", length = 1048576)
	 private byte[] imageData;
	@ManyToOne
	@JsonIgnore
	private User user;
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public long getEnrollment_no() {
		return enrollment_no;
	}
	public void setEnrollment_no(long enrollment_no) {
		this.enrollment_no = enrollment_no;
	}
	public long getRoll_no() {
		return roll_no;
	}
	public void setRoll_no(long roll_no) {
		this.roll_no = roll_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact_no_1() {
		return contact_no_1;
	}
	public void setContact_no_1(String contact_no_1) {
		this.contact_no_1 = contact_no_1;
	}
	public String getContact_no_2() {
		return contact_no_2;
	}
	public void setContact_no_2(String contact_no_2) {
		this.contact_no_2 = contact_no_2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getAadhaar_no() {
		return aadhaar_no;
	}
	public void setAadhaar_no(String aadhaar_no) {
		this.aadhaar_no = aadhaar_no;
	}
	public String getPan_no() {
		return pan_no;
	}
	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAdmission_year() {
		return admission_year;
	}
	public void setAdmission_year(String admission_year) {
		this.admission_year = admission_year;
	}
	public String getAdmission_type() {
		return admission_type;
	}
	public void setAdmission_type(String admission_type) {
		this.admission_type = admission_type;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public long getTenth_marks() {
		return tenth_marks;
	}
	public void setTenth_marks(long tenth_marks) {
		this.tenth_marks = tenth_marks;
	}
	public long getTenth_passing_year() {
		return tenth_passing_year;
	}
	public void setTenth_passing_year(long tenth_passing_year) {
		this.tenth_passing_year = tenth_passing_year;
	}
	public String getTenth_board() {
		return tenth_board;
	}
	public void setTenth_board(String tenth_board) {
		this.tenth_board = tenth_board;
	}
	public long getTwelth_marks() {
		return twelth_marks;
	}
	public void setTwelth_marks(long twelth_marks) {
		this.twelth_marks = twelth_marks;
	}
	public long getTwelth_passing_year() {
		return twelth_passing_year;
	}
	public void setTwelth_passing_year(long twelth_passing_year) {
		this.twelth_passing_year = twelth_passing_year;
	}
	public String getTwelth_board() {
		return twelth_board;
	}
	public void setTwelth_board(String twelth_board) {
		this.twelth_board = twelth_board;
	}
	public String getTwelth_stream() {
		return twelth_stream;
	}
	public void setTwelth_stream(String twelth_stream) {
		this.twelth_stream = twelth_stream;
	}
	public String getDiploma_course() {
		return diploma_course;
	}
	public void setDiploma_course(String diploma_course) {
		this.diploma_course = diploma_course;
	}
	public String getDiploma_branch() {
		return diploma_branch;
	}
	public void setDiploma_branch(String diploma_branch) {
		this.diploma_branch = diploma_branch;
	}
	public String getDiploma_marks() {
		return diploma_marks;
	}
	public void setDiploma_marks(String diploma_marks) {
		this.diploma_marks = diploma_marks;
	}
	public String getDiploma_passing_year() {
		return diploma_passing_year;
	}
	public void setDiploma_passing_year(String diploma_passing_year) {
		this.diploma_passing_year = diploma_passing_year;
	}
	public String getGraduation_university() {
		return graduation_university;
	}
	public void setGraduation_university(String graduation_university) {
		this.graduation_university = graduation_university;
	}
	public String getGraduation_course() {
		return graduation_course;
	}
	public void setGraduation_course(String graduation_course) {
		this.graduation_course = graduation_course;
	}
	public String getGraduation_branch() {
		return graduation_branch;
	}
	public void setGraduation_branch(String graduation_branch) {
		this.graduation_branch = graduation_branch;
	}
	public String getGraduation_marks() {
		return graduation_marks;
	}
	public void setGraduation_marks(String graduation_marks) {
		this.graduation_marks = graduation_marks;
	}
	public String getGraduation_passing_year() {
		return graduation_passing_year;
	}
	public void setGraduation_passing_year(String graduation_passing_year) {
		this.graduation_passing_year = graduation_passing_year;
	}
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getAadhaar_card() {
		return aadhaar_card;
	}
	public void setAadhaar_card(String aadhaar_card) {
		this.aadhaar_card = aadhaar_card;
	}
	public String getTenth_marksheet() {
		return tenth_marksheet;
	}
	public void setTenth_marksheet(String tenth_marksheet) {
		this.tenth_marksheet = tenth_marksheet;
	}
	public String getTwelth_marksheet() {
		return twelth_marksheet;
	}
	public void setTwelth_marksheet(String twelth_marksheet) {
		this.twelth_marksheet = twelth_marksheet;
	}
	public String getDiploma() {
		return diploma;
	}
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}
	public String getGraduation_degree() {
		return graduation_degree;
	}
	public void setGraduation_degree(String graduation_degree) {
		this.graduation_degree = graduation_degree;
	}
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Students_Details [s_id=" + s_id + ", enrollment_no=" + enrollment_no + ", roll_no=" + roll_no
				+ ", name=" + name + ", fatherName=" + fatherName + ", motherName=" + motherName + ", dob=" + dob
				+ ", gender=" + gender + ", email=" + email + ", contact_no_1=" + contact_no_1 + ", contact_no_2="
				+ contact_no_2 + ", address=" + address + ", district=" + district + ", state=" + state + ", pincode="
				+ pincode + ", aadhaar_no=" + aadhaar_no + ", pan_no=" + pan_no + ", religion=" + religion
				+ ", category=" + category + ", admission_year=" + admission_year + ", admission_type=" + admission_type
				+ ", department=" + department + ", course=" + course + ", branch=" + branch + ", tenth_marks="
				+ tenth_marks + ", tenth_passing_year=" + tenth_passing_year + ", tenth_board=" + tenth_board
				+ ", twelth_marks=" + twelth_marks + ", twelth_passing_year=" + twelth_passing_year + ", twelth_board="
				+ twelth_board + ", twelth_stream=" + twelth_stream + ", diploma_course=" + diploma_course
				+ ", diploma_branch=" + diploma_branch + ", diploma_marks=" + diploma_marks + ", diploma_passing_year="
				+ diploma_passing_year + ", graduation_university=" + graduation_university + ", graduation_course="
				+ graduation_course + ", graduation_branch=" + graduation_branch + ", graduation_marks="
				+ graduation_marks + ", graduation_passing_year=" + graduation_passing_year + ", tc=" + tc + ", cc="
				+ cc + ", aadhaar_card=" + aadhaar_card + ", tenth_marksheet=" + tenth_marksheet + ", twelth_marksheet="
				+ twelth_marksheet + ", diploma=" + diploma + ", graduation_degree=" + graduation_degree
				+ ", imageData=" + Arrays.toString(imageData) + ", user=" + user + "]";
	}
	public Students_Details() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	
	
}
