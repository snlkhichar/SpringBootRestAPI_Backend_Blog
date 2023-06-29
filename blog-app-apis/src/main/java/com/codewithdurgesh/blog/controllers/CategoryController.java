package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	   //Create user
		@PostMapping("/")
		public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
		{
			System.out.println("user controller:"+categoryDto);
			CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
			return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
		}
		
		//put mapping
		@PutMapping("/{catId}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("catId") Integer catId)
		{
			CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,catId);
			return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.CREATED);
		}
		
		//delete mapping
		@DeleteMapping("/{catId}")
		public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Integer catId)
		{
			this.categoryService.deleteCategory(catId);
			//return new ResponseEntity(Map.of("message","User deleted Successfully"),HttpStatus.OK);
			
			return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
		}


		@GetMapping("/{catId}")
		public ResponseEntity<CategoryDto> getCategory(@PathVariable("catId") Integer catId)
		{
			CategoryDto categoryDto = this.categoryService.getCategory(catId);
			
			return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
		} 
		
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getCategories()
		{
			List<CategoryDto> categories = this.categoryService.getCategories();
			
			return  ResponseEntity.ok(categories);
		} 
}
