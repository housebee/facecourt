package com.facecourt.webapp.persist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.UserArtifact;
import com.facecourt.webapp.model.UserArtifactKey;
import com.facecourt.webapp.model.VoteResultType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserArtifactDaoTest {

	@Autowired
	private ArtifactDao artifactDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserArtifactDao userArtifactDao;

	private User user1;

	private User user2;

	private Artifact artifact;

	@Before
	public void init() {
		user1 = new User();
		user1.setFirstName("FirstName");
		user1.setLastName("lastName");
		user1.setUsername("test@test.com");
		user1.setEmailVerified(Boolean.FALSE);
		user1.setPassword("password");

		userDao.save(user1);
		assertThat(user1.getId() != null);

		user2 = new User();
		user2.setFirstName("FirstName2");
		user2.setLastName("lastName2");
		user2.setUsername("test2@test.com");
		user2.setEmailVerified(Boolean.FALSE);
		user2.setPassword("password2");

		userDao.save(user2);
		assertThat(user2.getId() != null);

		artifact = new Artifact();
		artifact.setTitle("test");
		artifact.setDesc("description");

		artifactDao.save(artifact);
		assertThat(artifact.getId() != null);
	}

	@Test
	public void userArtifactTest() {
		List<UserArtifact> all = userArtifactDao.findAll();
		int existCount = all.size();

		UserArtifact userArtifact = new UserArtifact();

		UserArtifactKey compId = new UserArtifactKey();
		compId.setArtifactId(artifact.getId());
		compId.setUserId(user1.getId());

		userArtifact.setUserArtifactKey(compId);

		userArtifact.setVoteResult(VoteResultType.NEGATIVE);

		userArtifactDao.saveAndFlush(userArtifact);

		all = userArtifactDao.findAll();
		assertThat(all.size() == (existCount + 1));
		
		Set<UserArtifact> userArtSet = userArtifactDao.findByUserArtifactKeyArtifactId(artifact.getId());
		assertThat(userArtSet.size() == 1);
		
		userArtSet = userArtifactDao.findByUserArtifactKeyUserId(user1.getId());
		assertThat(userArtSet.size() == 1);
		
		userArtSet = userArtifactDao.findByUserArtifactKeyUserId(user2.getId());
		assertThat(userArtSet.size() == 0);
		
		userArtifact.setVoteResult(VoteResultType.POSITIVE);
		userArtifactDao.saveAndFlush(userArtifact);
		
		Optional<UserArtifact> userArt2 = userArtifactDao.findById(compId);
		VoteResultType voteResult = userArt2.map(UserArtifact::getVoteResult).orElse(VoteResultType.UNKNOWN);
		System.out.println("\n\nResult = " + userArt2);
		System.out.println("\n\nResult = " + voteResult);
		assertThat(compId.equals(userArt2.get().getUserArtifactKey()));
		assertThat(voteResult == VoteResultType.UNKNOWN);
		
//		voteResult = userArt2.flatMap(UserArtifact::getVoteResult).orElse(VoteResultType.UNKNOWN);
	}

}
