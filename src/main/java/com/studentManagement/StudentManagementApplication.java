package com.studentManagement;

import com.studentManagement.model.*;
import com.studentManagement.repository.SchoolRepository;
import com.studentManagement.repository.StudentRepository;
import com.studentManagement.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class StudentManagementApplication implements CommandLineRunner {
	private final SchoolRepository schoolRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		School school = new School("sdsafdfs");

		Teacher teacher1 = new Teacher("asdad", "adsad", "qwewe.com");
		Teacher teacher2 = new Teacher("as13", "aadsd", "23ewe.com");

		Student student1 = new Student("akdl", "alsdk", "sl;d", StudentLevel.GENIUS);
		Student student2 = new Student("23l", "a=9239", "ss.dld", StudentLevel.GENIUS);
		Student student3 = new Student("ofkdo2l", "sladko", "ewrfd", StudentLevel.GENIUS);

		Lesson lesson1 = new Lesson(LessonType.UNIT, LessonSubject.MATH);
		Lesson lesson2 = new Lesson(LessonType.UNIT, LessonSubject.BIOLOGY);
		Lesson lesson3 = new Lesson(LessonType.UNIT, LessonSubject.PHYSICS);

		teacher1.getStudents().add(student1);
		teacher1.getStudents().add(student2);
		teacher2.getStudents().add(student3);

		teacher1.getLessons().add(lesson1);
		teacher1.getLessons().add(lesson2);
		teacher2.getLessons().add(lesson2);
		teacher2.getLessons().add(lesson3);

		lesson1.getTeachers().add(teacher1);
		lesson2.getTeachers().add(teacher1);
		lesson2.getTeachers().add(teacher2);
		lesson3.getTeachers().add(teacher2);


		student1.getLessons().add(lesson1);
		student1.getLessons().add(lesson2);
		student2.getLessons().add(lesson1);
		student2.getLessons().add(lesson2);
		student3.getLessons().add(lesson3);
		student3.getLessons().add(lesson1);

		lesson1.getStudents().add(student1);
		lesson1.getStudents().add(student3);
		lesson1.getStudents().add(student2);
		lesson2.getStudents().add(student1);
		lesson2.getStudents().add(student2);
		lesson3.getStudents().add(student3);

		school.getTeachers().add(teacher1);
		school.getTeachers().add(teacher2);

		school.getStudents().add(student1);
		school.getStudents().add(student2);
		school.getStudents().add(student3);

		schoolRepository.save(school);
	}
}
