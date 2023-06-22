package com.spring.blog;

import com.spring.blog.model.Role;
import com.spring.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Blog application REST APIs [Spring boot]",
				description = "This is client consumable blog post page application REST APIs documentation",
				version = "V1.0",
				contact = @Contact(
						name = "Idrissa Muvunyi",
						email = "muvunyiiddy@gmail.com",
						url = "https://idrissa.netlify.app/"
				),
				license = @License(
					name = "Apache 2.0",
					url = "idrissa.netlify.app"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/idMuvunyi/blogApp"
		)
)
public class SpringbootBlogRestApiApplication implements CommandLineRunner {
	@Bean
     public ModelMapper modelMapper(){
		 return new ModelMapper();
	 }
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;
	@Override
	public void run(String... args) throws Exception {
		// Role admin set
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		// Role user set
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
	}
}
