package com.codewithdurgesh.blog.payloads;

import com.codewithdurgesh.blog.entities.Post;

public class CommentDto {
	
    private int id;
	private String content;
	private Post post;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	@Override
	public String toString() {
		return "CommentDto [id=" + id + ", content=" + content + ", post=" + post + "]";
	}
	
	
	

}
