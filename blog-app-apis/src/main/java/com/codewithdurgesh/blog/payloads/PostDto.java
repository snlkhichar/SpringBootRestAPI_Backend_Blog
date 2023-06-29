package com.codewithdurgesh.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Comment;
import com.codewithdurgesh.blog.entities.User;

public class PostDto {
	private Integer postId;

	private String title;

	private String content;

	private String imageName;

	private Date addedDate;

	private Category category;
	private User user;

	 private Set<Comment> comments = new HashSet<>();

	//private Set<CommentDto> comments = new HashSet<>();
	// private CategoryDto categoryDto;
	// private UserDto userDto;

	public PostDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return postId;
	}

	public void setId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * public CategoryDto getCategoryDto() { return categoryDto; }
	 * 
	 * public void setCategoryDto(CategoryDto categoryDto) { this.categoryDto =
	 * categoryDto; }
	 * 
	 * public UserDto getUserDto() { return userDto; }
	 * 
	 * public void setUserDto(UserDto userDto) { this.userDto = userDto; }
	 */

	
	  public Set<Comment> getComments() { return comments; }
	  
	  
	  
	  public void setComments(Set<Comment> comments) { this.comments = comments; }
	 

	@Override
	public String toString() {
		return "PostDto [title=" + title + ", content=" + content + ", imageName=" + imageName + "]";
	}

	/*
	 * public Set<CommentDto> getComments() { return comments; }
	 * 
	 * 
	 * 
	 * public void setComments(Set<CommentDto> comments) { this.comments = comments;
	 * }
	 */

}
