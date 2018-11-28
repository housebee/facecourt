package com.facecourt.webapp.persist;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.facecourt.webapp.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Before
	public void init() {
	}

	@Test
	public void userTest() {
		User user = new User();
		user.setEmailVerified(Boolean.FALSE);
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("password");
		user.setUsername("test");

		// save user
		userDao.save(user);
		userDao.flush();

		List<User> userList = userDao.findAll();
		assertTrue(userList.size() == 3);

		user = new User();
		user.setEmailVerified(Boolean.FALSE);
		user.setFirstName("test2");
		user.setLastName("test2");
		user.setPassword("password2");
		user.setUsername("test2");

		// save user
		userDao.save(user);
		userDao.flush();

		userList = userDao.findAll();
		assertTrue(userList.size() == 4);

		// update
		user.setFirstName("newName");
		userDao.save(user);

		userList = userDao.findAll();

		for (User user2 : userList) {
			System.out.println(user2);
		}

		// find the updated one
		List<User> userList2 = userList.stream().filter(u -> "newName".equalsIgnoreCase(u.getFirstName()))
				.collect(Collectors.toList());

		assertTrue(userList2.size() == 1);
	}

}
