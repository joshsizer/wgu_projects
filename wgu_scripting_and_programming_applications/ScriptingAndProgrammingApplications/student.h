#pragma once
#include "degree.h"
#include <string>
#ifndef STUDENT_H
#define STUDENT_H

class Student {
	std::string student_id;
	std::string first_name;
	std::string last_name;
	std::string email_address;
	int age;
	int days_in_course[3];
	DegreeProgram degree_program;

public:
	Student(std::string, std::string, std::string,
		std::string, int age, int[3], DegreeProgram);
	Student(std::string, std::string, std::string,
		std::string, int age, int, int, int, DegreeProgram);
	std::string get_student_id(void);
	std::string get_first_name(void);
	std::string get_last_name(void);
	std::string get_email_address(void);
	int get_age(void);
	int* get_days_in_course(void);
	DegreeProgram get_degree_program(void);

	void set_student_id(std::string);
	void set_first_name(std::string);
	void set_last_name(std::string);
	void set_email_address(std::string);
	void set_age(int);
	void set_days_in_course(int*);
	void set_degree_program(DegreeProgram);

	void print();


	static DegreeProgram degree_program_from_string(std::string);
	static std::string string_from_degree_program(DegreeProgram);

private:
	void init(std::string, std::string, std::string,
		std::string, int, int[3], DegreeProgram);
};
#endif