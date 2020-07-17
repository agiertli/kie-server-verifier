package org.redhat.services.redhatverifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedhatVerifierApplication {
	
	private static Logger logger = LoggerFactory.getLogger(RedhatVerifierApplication.class);

	public static void main(String[] args) {
		
	logger.info("truststore {}",System.getProperty("javax.net.ssl.trustStore"));
	logger.info("truststore pw {}",System.getProperty("javax.net.ssl.trustStorePassword"));


		KieServerContainerVerifier.executor(args);
		SpringApplication.run(RedhatVerifierApplication.class, args);
	}

}
