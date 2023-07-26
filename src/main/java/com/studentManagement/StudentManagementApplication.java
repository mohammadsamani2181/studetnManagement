package com.studentManagement;

import com.studentManagement.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
@RequiredArgsConstructor
//@EnableTransactionManagement
public class StudentManagementApplication implements CommandLineRunner {
	private final SchoolRepository schoolRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
