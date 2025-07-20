package com.mh.mhbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MhBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MhBackendApplication.class, args);

		// Open Swagger UI automatically in default browser
		String url = "http://localhost:8081/swagger-ui/index.html";
		try {
			if (java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			} else {
				System.out.println("Desktop is not supported. Please open " + url + " manually.");
			}
		} catch (Exception e) {
			System.out.println("Failed to open browser: " + e.getMessage());
		}
	}

}
