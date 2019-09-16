package com.facecourt.webapp.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.Court;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.UserArtifact;
import com.facecourt.webapp.model.VoteResultType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtifactDaoTest {

	@Autowired
	private ArtifactDao artifactDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CourtDao courtDao;
	
	private Court publicCourt;

	@SuppressWarnings("static-access")
	@Before
	public void init() {
		publicCourt = courtDao.getOne(publicCourt.PUBLIC_COURT_ID);
	}

	@Test
	public void userArtifactTest() {
		// given
		Artifact artifact = new Artifact();
		artifact.setTitle("test");
		artifact.setDesc("description");

		artifactDao.save(artifact);

		User user = new User();
		user.setEmailVerified(Boolean.FALSE);
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("password");
		user.setUsername("test");

		// save user
		userDao.save(user);

		UserArtifact userArtifact = new UserArtifact();
//		userArtifact.setUser(user);
//		userArtifact.setArtifact(artifact);
		userArtifact.setVoteResult(VoteResultType.NEGATIVE);

//		user.getUserArtifacts().add(userArtifact);
//		artifact.getUserArtifacts().add(userArtifact);
		
		artifact.setOwner(user);
		artifact.setCourt(publicCourt);

		// userDao.save(user); // error, cannot understand it !
//		artifactDao.flush();
		// artifactDao.save(artifact);
		// userDao.save(user); // this is OK. why both one-to-many, it has
		// sequence dependency?

		assertThat(artifact.getId() != null);

		User foundUser = userDao.getOne(user.getId());
		assertThat(foundUser.getFirstName().equals(user.getFirstName()));
//		for (UserArtifact ua : foundUser.getUserArtifacts()) {
//			assertThat(ua.getUser().getId() == user.getId());
//			assertThat(ua.getArtifact().getId() == artifact.getId());
//			assertThat(ua.getVoteResult() == userArtifact.getVoteResult());
//		}

//		assertThat(user.getUserArtifacts().size() == 1);
//		user.getUserArtifacts().remove(userArtifact);
		userDao.save(user);
		userDao.flush();
		foundUser = userDao.getOne(user.getId());
//		assertThat(foundUser.getUserArtifacts().size() == 0);

		Artifact foundArtifact = artifactDao.getOne(artifact.getId());
		assertThat(foundArtifact.getTitle()).isEqualTo(artifact.getTitle());

//		for (UserArtifact ua : foundArtifact.getUserArtifacts()) {
//			assertThat(ua.getUser().getId() == user.getId());
//			assertThat(ua.getArtifact().getId() == artifact.getId());
//			assertThat(ua.getVoteResult() == userArtifact.getVoteResult());
//		}
		
		List<Object[]> allCasesWithCount = artifactDao.findAllWithCount();
		
		for (Object[] objList : allCasesWithCount) {
			System.out.println(objList[0]);//id
			System.out.println(objList[1]);//title
			System.out.println(objList[2]);//description
			System.out.println(objList[3]);//pos total
			System.out.println(objList[4]);//neg total
		}
		

		List<Object[]> existingArtifact = artifactDao.findArtifactWithCount(artifact.getId());
		
		System.out.println("------------retrieve artifact with votes---------------------");
		
		existingArtifact.stream().forEach((record) -> {
			Artifact other = new Artifact();
			other.setId((Long.valueOf((int)record[0])));
			other.setTitle((String)record[1]);
			other.setDesc((String)record[2]);
			other.setTotalPos(((BigInteger)record[3]).longValue());
			other.setTotalNeg(((BigInteger)record[4]).longValue());
			
			System.out.println(other);
		});
		
		
	}

}
