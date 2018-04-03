package com.facecourt.webapp.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facecourt.webapp.model.Post;

public interface PostDao extends JpaRepository<Post, Long> {

}