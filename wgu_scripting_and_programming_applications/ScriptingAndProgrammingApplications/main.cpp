// ScriptingAndProgrammingApplications.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <string>
#include "student.h"
#include "roster.h"


int main()
{
    std::cout << "Scripting and Programming - Applications\nProgramming Language: C++\nStudent ID: 001216849\nName: Joshua Sizer\n\n";

    const std::string studentData[] = { "A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY", 
        "A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK", 
        "A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE", 
        "A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY", 
        "A5,Joshua,Sizer,jsizer@wgu.edu,20,30,35,40,SOFTWARE" };

    Roster* myRoster = new Roster();

    for (int i = 0; i < 5; i++) {
        std::string current_student_info = studentData[i];
        std::string delimiter = ",";
        int pos = 0;
        std::string token;
        int counter = 0;
        std::string student_id;
        std::string first_name;
        std::string last_name;
        std::string email_address;
        int age;
        int days1;
        int days2;
        int days3;
        std::string degree_program;
        while ((pos = current_student_info.find(delimiter)) != std::string::npos) {
            token = current_student_info.substr(0, pos);
            current_student_info.erase(0, pos + delimiter.length());
            switch (counter) {
                case 0:
                    student_id = token;
                    break;
                case 1:
                    first_name = token;
                    break;
                case 2:
                    last_name = token;
                    break;
                case 3:
                    email_address = token;
                    break;
                case 4:
                    age = std::stoi(token);
                    break;
                case 5:
                    days1 = std::stoi(token);
                case 6:
                    days2 = std::stoi(token);
                    break;
                case 7:
                    days3 = std::stoi(token);
                    break;
            }
            counter++;
        }
        degree_program = current_student_info;
        myRoster->add(student_id, first_name, last_name, email_address, age, days1, days2, days3, Student::degree_program_from_string(degree_program));
    }

    myRoster->printAll();
    std::cout << "Invalid emails: " << std::endl;
    myRoster->printInvalidEmails();
    std::cout << std::endl;
    std::cout << "Average days in course for each student: " << std::endl;

    for (int i = 0; i < myRoster->length(); i++) {
        std::cout << myRoster->get(i)->get_student_id() << " ";
        myRoster->printAverageDaysInCourse(myRoster->get(i)->get_student_id());
    }

    std::cout << std::endl;
    std::cout << "Printing by degree program Software: " << std::endl;
    myRoster->printByDegreeProgram(DegreeProgram::SOFTWARE);
    myRoster->remove("A3");
    std::cout << std::endl << "Removed A3" << std::endl;
    myRoster->printAll();
    myRoster->remove("A3");

    delete myRoster;
}