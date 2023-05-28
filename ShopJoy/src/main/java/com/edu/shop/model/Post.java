package com.edu.shop.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	private int postId;
	private String title;
	private String content;
	private String image;
	private Date postDate; 
	

}
