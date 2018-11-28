package com.facecourt.webapp.persist;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.facecourt.webapp.model.Comment;
import com.facecourt.webapp.model.Post;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostDaoTest {

	@Autowired
	private PostDao postDao;

	private Post post;

	@Before
	public void init() {
		Post post1 = new Post();
		post1.setName("Test1");

		Comment comment1 = new Comment();
		comment1.setReview("comment 1");
		
		post1.addComment(comment1);
		
		post = postDao.save(post1);
		postDao.flush();
	}

	@After
	public void end() {
		postDao.deleteAll();
	}

	@Test
	public void oneToManyTest() {

		Comment comment2 = new Comment();
		comment2.setReview("comment 2");
		
		post.addComment(comment2);
		
		postDao.save(post);
		postDao.flush();
	
		Post post2 = postDao.getOne(post.getId());
		
		assertThat(post2 != null);
		assertThat(post2.getComments() != null);
		assertThat(post2.getComments().size() == 2);
	}

}
