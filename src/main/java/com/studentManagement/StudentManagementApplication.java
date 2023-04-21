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

	}
}
