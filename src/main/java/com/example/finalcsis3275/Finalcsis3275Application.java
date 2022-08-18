package com.example.finalcsis3275;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Finalcsis3275Application {

	public static void main(String[] args) {
		SpringApplication.run(Finalcsis3275Application.class, args);
	}


    @Bean
	CommandLineRunner commandLineRunner(LoanRepository loanRepository){
        return args -> {
			loanRepository.save(new loantable("1157", "Joy Ramirez", 100000.0, 5,"Business"));
			loanRepository.save(new loantable("1005", "Josaphat Dee", 5000.0, 5,"Business"));
			loanRepository.save(new loantable("1006", "Jessica Bane", 15000.0, 1,"Personal"));
			loanRepository.save(new loantable("9999", "Ravleen test", 1000.0, 1,"Business"));
			loanRepository.save(new loantable("1012", "Johnny Jacobi", 8000.0, 5,"Business"));
//			loanRepository.findAll().forEach(p->{
//                System.out.println(p.getClientname());
//            });
        };
    }
}
