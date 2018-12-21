package com.facecourt.webapp.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.facecourt.webapp.model.Artifact;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtifactServiceTest {

	@Autowired
	private ArtifactService artifactService;

	@TestConfiguration
    static class ArtifactServiceTestContextConfiguration {
  
        @Bean
        public ArtifactService artifactService() {
            return new ArtifactService();
        }
    }
	
	@Test
	public void test() {

		Artifact artifact = new Artifact();
		artifact.setDesc("new artifact");
		artifact.setTitle("new title");
		artifactService.createArtifact(artifact, "admin");

		assertTrue("public court id match. ", artifact.getId() != null);

	}

}
