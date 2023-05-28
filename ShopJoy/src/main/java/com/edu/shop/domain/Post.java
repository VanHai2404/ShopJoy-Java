package com.edu.shop.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	@Column(columnDefinition = "nvarchar(200) not null")
	private String title;
	@Column(columnDefinition = "nvarchar(900) not null")
	private String content;
	@Column(columnDefinition = "nvarchar(100) not null")
	private String image;
	@Temporal(TemporalType.DATE)
	private Date postDate; 
	

}
