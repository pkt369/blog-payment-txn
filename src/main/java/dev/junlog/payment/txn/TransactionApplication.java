package dev.junlog.payment.txn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionApplication {

	public static void main(String[] args) {
        System.out.println("hi");
		SpringApplication.run(TransactionApplication.class, args);
	}

}
