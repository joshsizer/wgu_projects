#include "roster.h"
#include <string>
#include <iostream>
#include <ctype.h>
#define START_SIZE 10

Roster::Roster() {
	class_roster_array = new Student*[START_SIZE];
	class_roster_size = START_SIZE;
	num_students = 0;
}

Roster::~Roster() {
	delete[] class_roster_array;
}

void Roster::add(std::string student_id, std::string first_name, std::string last_name, std::string email_address,
	int age, int days_in_course1, int days_in_course2, int days_in_course3, DegreeProgram degree_program) {

	Student* newStudent = new Student(student_id, first_name, last_name, email_address, age,
		days_in_course1, days_in_course2, days_in_course3, degree_program);

	if (num_students >= class_roster_size) {
		resize_class_roster();
	}
	class_roster_array[num_students] = newStudent;
	num_students++;
}

void Roster::remove(std::string student_id) {
	int index = -1;
	for (int i = 0; i < num_students; i++) {
		if (class_roster_array[i]->get_student_id() == student_id) {
			index = i;
		}
	}

	if (index == -1) {
		std::cout << "Error: student " << student_id << " not found." << std::endl;
		return;
	}

	for (int i = index; i < num_students - 1; i++) {
		class_roster_array[i] = class_roster_array[i + 1];
	}
	num_students = num_students - 1;
}

void Roster::printAll() {
	for (int i = 0; i < num_students; i++) {
		class_roster_array[i]->print();
		std::cout << std::endl;
	}
}
void Roster::printAverageDaysInCourse(std::string student_id) {
	Student* student = NULL;
	for (int i = 0; i < num_students; i++) {
		if (class_roster_array[i]->get_student_id() == student_id) {
			student = class_roster_array[i];
		}
	}

	if (student == NULL) {
		std::cout << "Error: student " << student_id << " not found." << std::endl;
		return;
	}
	double avg = 0.0;
	int* days_in_class = student->get_days_in_course();
	for (int i = 0; i < 3; i++) {
		avg += days_in_class[i];
	}
	avg = avg / 3.0;
	std::cout << "Average days in course: " << avg << std::endl;
}

void Roster::printInvalidEmails() {
	for (int i = 0; i < num_students; i++) {
		Student* current_student = class_roster_array[i];
		std::string current_email = current_student->get_email_address();
		bool found_period = false;
		bool found_at = false;
		bool found_space = false;
		for (int j = 0; j < current_email.length(); j++) {
			char cur_char = current_email[j];

			if (cur_char == '.') {
				found_period = true;
			}
			if (cur_char == '@') {
				found_at = true;
			}
			if (isspace(cur_char)) {
				found_space = true;
			}
		}

		if (!found_period || !found_at || found_space) {
			std::cout << current_email << std::endl;
		}
	}
}
void Roster::printByDegreeProgram(DegreeProgram degreeProgram) {
	bool found = false;
	for (int i = 0; i < num_students; i++) {
		if (class_roster_array[i]->get_degree_program() == degreeProgram) {
			class_roster_array[i]->print();
			found = true;
		}
	}

	if (found == false) {
		std::cout << "Error: no student with degree type found." << std::endl;
	}
}

void Roster::resize_class_roster() {
	int new_size = class_roster_size * 2;

	Student** new_class_roster_array = new Student * [new_size];
	memcpy(new_class_roster_array, class_roster_array, class_roster_size);
	class_roster_size = new_size;
	delete [] class_roster_array;
	class_roster_array = new_class_roster_array;
}

Student* Roster::get(int index) {
	if (index >= num_students) {
		return NULL;
	}
	else {
		return class_roster_array[index];
	}
}

int Roster::length() {
	return num_students;
}