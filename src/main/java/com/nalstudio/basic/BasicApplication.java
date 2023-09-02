package com.nalstudio.basic;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableAutoConfiguration 어노테이션은 Spring Boot에서 자동설정을 가능하게 해주는데,
// myBatis를 사용하기 위해서는 DataSource에 대한 구현체를 직접등록해야 되기 때문에
// 아래와 같이 DataSource 및 TransactionManager에 대한 자동설정을 제외
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class})
//@EnableScheduling//scheduling 작업 필요시
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}


  // test git hub App
}
