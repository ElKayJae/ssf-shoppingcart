package com.vttp2022.shoppingcart;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import static com.vttp2022.shoppingcart.util.IOUtil.*;

@SpringBootApplication
public class ShoppingcartApplication {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingcartApplication.class);
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ShoppingcartApplication.class);
		DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
		List<String> opsVal = appArgs.getOptionValues("dataDir");
		if (opsVal != null){
			logger.info("data dir > "+ opsVal.get(0));
			createDir( opsVal.get(0));
		} else {
			logger.warn("No data directory was provided");
			System.exit(1);
		}
		app.run(args);
	}

}
