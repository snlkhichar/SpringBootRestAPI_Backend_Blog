package com.codewithdurgesh.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.execeptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.repositories.RoleRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
   @Autowired
	private UserRepo userRepo;
   
   @Autowired
   private ModelMapper modelMapper;
   
   @Autowired
   private PasswordEncoder passwordEncoder;
   
   @Autowired
   private RoleRepo roleRepo;

	//@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		System.out.println("savedUser in service:"+user);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);	
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		UserDto userToDto1 = this.userToDto(updatedUser);
		return userToDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
	
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->userToDto(user)).collect(Collectors.toList());
		System.out.println("userDtos"+userDtos);
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
      
	}
	
	
	  public User dtoToUser(UserDto userDto) {
		  
		  User user = this.modelMapper.map(userDto,User.class);
		  
		  //user.setId(userDto.getId());
		 //user.setName(userDto.getName());
		 //user.setEmail(userDto.getEmail());
		 //user.setAbout(userDto.getAbout());
		 //user.setPassword(userDto.getPassword());
		  return user;
	  
	  }
	  
	  public UserDto userToDto(User user)
	  {
		  UserDto userDto = this.modelMapper.map(user,UserDto.class);
		  
		  //userDto.setId(user.getId());
		  //userDto.setName(user.getName());
		  //userDto.setEmail(user.getEmail());
		  //userDto.setPassword(user.getPassword());
		  //userDto.setAbout(user.getAbout());
		  
		  return userDto;
	  }

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto,User.class);
		//encoded the pwd
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
	 

}
