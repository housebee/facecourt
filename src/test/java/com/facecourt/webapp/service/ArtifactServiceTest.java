package com.facecourt.webapp.service;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.VoteResultType;
import com.facecourt.webapp.persist.UserDao;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtifactServiceTest {

	@Autowired
	private ArtifactService artifactService;

	@Autowired
	private UserDao userDao;

	private User user;

	private Artifact artifact;

	@TestConfiguration
	static class ArtifactServiceTestContextConfiguration {

		@Bean
		public ArtifactService artifactService() {
			return new ArtifactService();
		}
	}

	@Before
	public void init() {
		artifact = new Artifact();
		artifact.setTitle("test");
		artifact.setDesc("description");

		artifactService.createArtifact(artifact, "admin");

		user = new User();
		user.setEmailVerified(Boolean.FALSE);
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("password");
		user.setUsername("test");

		// save user
		userDao.save(user);
	}

	@Test
	public void testSave() {
		Artifact artifact = new Artifact();
		artifact.setDesc("new artifact");
		artifact.setTitle("new title");
		artifactService.createArtifact(artifact, "admin");

		assertTrue("created artifact. ", artifact.getId() != null);

		Artifact artifact2 = artifactService.getArtifactById(artifact.getId());
		assertTrue("find artifact by id. ", artifact.getId().longValue() == artifact2.getId().longValue());
	}

	@Test
	public void testUesrArtifact() throws Exception {
//		artifactService.voteArtifact("admin", artifact.getId(), VoteResultType.POSITIVE);
//
//		Map<Long, Long> result = artifactService.getArtifactVoteNum(artifact);
//		assertTrue(result.size() == 1);
//		assertTrue(result.get(Long.valueOf(VoteResultType.POSITIVE.getCode())) == 1);
//
//		String content = result.entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
//				.collect(Collectors.joining(", "));
//		System.out.println(content);
//
//		artifactService.voteArtifact("test", artifact.getId(), VoteResultType.POSITIVE);
//
//		result = artifactService.getArtifactVoteNum(artifact);
//
//		content = result.entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
//				.collect(Collectors.joining(", "));
//		System.out.println(content);
//
//		artifactService.voteArtifact("test", artifact.getId(), VoteResultType.NEGATIVE);
//		assertTrue(result.size() == 1);
//		assertTrue(result.get(Long.valueOf(VoteResultType.POSITIVE.getCode())) == 2);
//
//		result = artifactService.getArtifactVoteNum(artifact);
//		assertTrue(result.size() == 2);
//		assertTrue(result.get(Long.valueOf(VoteResultType.POSITIVE.getCode())) == 1);
//		assertTrue(result.get(Long.valueOf(VoteResultType.NEGATIVE.getCode())) == 1);
//
//		content = result.entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
//				.collect(Collectors.joining(", "));
//		System.out.println(content);
		List<Artifact> artifactList = this.artifactService.getAllArtifactsAndVotes();
		
		assertTrue(artifactList.size() == 2);
		
		System.out.println("S.SUN::Get all artifacts......");
		
		for (Artifact artifact : artifactList) {
			System.out.println(artifact);
		}
		
		artifactService.voteArtifact("test", 1L, VoteResultType.POSITIVE);
		
		Artifact artfactWithUpdatedVote = artifactService.findArtifactWithVotes(1L);

		System.out.println("S.SUN::Add one positive vote ......");
		System.out.println(artfactWithUpdatedVote);
	}

}
