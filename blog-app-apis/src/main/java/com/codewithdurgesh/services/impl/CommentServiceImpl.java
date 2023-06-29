package com.codewithdurgesh.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Comment;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.execeptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.CommentDto;
import com.codewithdurgesh.blog.repositories.CommentRepo;
import com.codewithdurgesh.blog.repositories.PostRepo;
import com.codewithdurgesh.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
		Comment comment=this.modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
	
		Comment savedComment=this.commentRepo.save(comment);
		System.out.println("comment:"+savedComment);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment com = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "CsommentId", commentId));
		this.commentRepo.delete(com);
	}

}
