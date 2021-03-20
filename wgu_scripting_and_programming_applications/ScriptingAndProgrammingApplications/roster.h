#pragma once

#ifndef ROSTER_H
#define ROSTER_H

#include "student.h"

class Roster {

	Student** class_roster_array;
	int class_roster_size;
	int num_students;

public:
	Roster();
	~Roster();
	void add(std::string student_id, std::string first_name, std::string last_name, std::string email_address,
		int age, int days_in_course1, int days_in_course2, int days_in_course3, DegreeProgram degree_program);

	void remove(std::string student_id);

	void printAll();
	void printAverageDaysInCourse(std::string student_id);
	void printInvalidEmails();
	void printByDegreeProgram(DegreeProgram degreeProgram);

	Student* get(int index);
	int length();

	void resize_class_roster();
};

#endif
