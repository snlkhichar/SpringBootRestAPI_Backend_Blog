package com.codewithdurgesh.blog;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.repositories.RoleRepo;


@SpringBootApplication(scanBasePackages={
		"com.codewithdurgesh.blog.controllers", "com.codewithdurgesh.services.impl","com.codewithdurgesh.blog.repositories","com.codewithdurgesh.blog.payloads","com.codewithdurgesh.blog.entities","com.codewithdurgesh.blog.execeptions","com.codewithdurgesh.blog.config","com.codewithdurgesh.blog.security","com.codewithdurgesh.services"})
public class BlogAppApisApplication implements CommandLineRunner
{
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception 
	{
		System.out.println(this.passwordEncoder.encode("123"));
		
		try
		{
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);
			roles.add(role1);
			List<Role>result=this.roleRepo.saveAll(roles);
			result.forEach(r->System.out.println(r.getName()));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

}
