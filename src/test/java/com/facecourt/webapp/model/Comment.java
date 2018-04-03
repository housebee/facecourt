package com.facecourt.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Post post;

	private String review;

	public void setPost(Post post) {
		this.post = post;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	/*
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Comment))
			return false;
		return id != null && id.equals(((Comment) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}
	*/
}
