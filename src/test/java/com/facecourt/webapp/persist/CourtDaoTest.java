package com.facecourt.webapp.persist;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.facecourt.webapp.model.Court;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CourtDaoTest {

	@Autowired
	private CourtDao courtDao;

	@Test
	public void test() {

		Court publicCourt = courtDao.findOne(Court.PUBLIC_COURT_ID);

		assertTrue("public court id match. ", publicCourt.getId() == Court.PUBLIC_COURT_ID);

	}

}
