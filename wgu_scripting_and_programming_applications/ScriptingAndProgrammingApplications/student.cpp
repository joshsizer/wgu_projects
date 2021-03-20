#include "student.h"
#include <iostream>

Student::Student(std::string _student_id, std::string _first_name, std::string _last_name,
	std::string _email_address, int _age, int _days_in_course[3], DegreeProgram _degree_program) {
	init(_student_id, _first_name, _last_name, _email_address, _age, _days_in_course, _degree_program);
}

Student::Student(std::string _student_id, std::string _first_name, std::string _last_name,
	std::string _email_address, int _age, int days1, int days2, int days3, DegreeProgram _degree_program) {
	int days_in_course[3];
	days_in_course[0] = days1;
	days_in_course[1] = days2;
	days_in_course[2] = days3;
	init(_student_id, _first_name, _last_name, _email_address, _age, days_in_course, _degree_program);
}

void Student::init(std::string _student_id, std::string _first_name, std::string _last_name,
	std::string _email_address, int _age, int _days_in_course[3], DegreeProgram _degree_program) {
	student_id = _student_id;
	first_name = _first_name;
	last_name = _last_name;
	email_address = _email_address;
	age = _age;
	for (int i = 0; i < 3; i++) {
		days_in_course[i] = _days_in_course[i];
	}
	degree_program = _degree_program;
}


std::string Student::get_student_id(void) {
	return student_id;
}

std::string Student::get_first_name(void) {
	return first_name;
}

std::string Student::get_last_name(void) {
	return last_name;
}

std::string Student::get_email_address(void) {
	return email_address;
}
int Student::get_age(void) {
	return age;
}

int* Student::get_days_in_course(void) {
	return days_in_course;
}

DegreeProgram Student::get_degree_program(void) {
	return degree_program;
}

void Student::set_student_id(std::string new_id) {
	student_id = new_id;
}

void Student::set_first_name(std::string new_first_name) {
	first_name = new_first_name;
}

void Student::set_last_name(std::string new_last_name) {
	last_name = new_last_name;
}

void Student::set_email_address(std::string new_email_address) {
	email_address = new_email_address;
}

void Student::set_age(int new_age) {
	age = new_age;
}

void Student::set_days_in_course(int* new_days_in_course) {
	for (int i = 0; i < 3; i++) {
		days_in_course[i] = new_days_in_course[i];
	}
}

void Student::set_degree_program(DegreeProgram new_degree_program) {
	degree_program = new_degree_program;
}

void Student::print() {
	std::cout << get_student_id() << "\t";
	std::cout << "First Name: " << get_first_name() << "\t";
	std::cout << "Last Name: " << get_last_name() << "\t";
	std::cout << "Age: " << get_age() << "\t";
	int* class_sizes = get_days_in_course();
	std::cout << "daysInCourse: {";
	for (int i = 0; i < 3; i++) {
		std::cout << class_sizes[i];
		if (i == 2) {
			std::cout << "} ";
		}
		else {
			std::cout << ", ";
		}
	}
	std::cout << "Degree Program: " << Student::string_from_degree_program(get_degree_program()) << std::endl;
}

DegreeProgram Student::degree_program_from_string(std::string input) {
	if (!input.compare("SOFTWARE")) {
		return DegreeProgram::SOFTWARE;
	}
	else if (!input.compare("NETWORK")) {
		return DegreeProgram::NETWORK;
	}
	else if (!input.compare("SECURITY")) {
		return DegreeProgram::SECURITY;
	}
}

std::string Student::string_from_degree_program(DegreeProgram dp) {
	if (dp == DegreeProgram::SOFTWARE) {
		return "Software";
	}
	else if (dp == DegreeProgram::NETWORK) {
		return "Network";
	}
	else if (dp == DegreeProgram::SECURITY) {
		return "Security";
	}
}