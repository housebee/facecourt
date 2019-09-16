package com.facecourt.webapp.persist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.User;

public interface ArtifactDao extends JpaRepository<Artifact, Long> {

	/**
	 * Find all artifacts by owner
	 * 
	 * enum is saved in database in the order, starting from 0. Therefore
	 * Negative: 0
	 * Unknown: 1
	 * Positive: 2
	 * 
	 * @param owner
	 * @return owner created artifacts
	 */
	List<Artifact> findByOwner(User owner);

	@Query(value = "select a.id, a.title, a.description, (SELECT  COUNT(*) FROM userartifact ua WHERE   ua.artifactid = a.id and voteresult = 2) AS posTotal, (SELECT  COUNT(*) FROM userartifact ua WHERE ua.artifactid = a.id and voteresult = 0) AS negTotal	from artifact a", nativeQuery = true)
	List<Object[]> findAllWithCount();	

	/**
	 * enum is saved in database in the order, starting from 0. Therefore
	 * Negative: 0
	 * Unknown: 1
	 * Positive: 2
	 * 
	 * @param artifactId
	 * @return
	 */
	@Query(value = "select a.id, a.title, a.description, (SELECT  COUNT(*) FROM userartifact ua WHERE   ua.artifactid = a.id and voteresult = 2) AS posTotal, (SELECT  COUNT(*) FROM userartifact ua WHERE ua.artifactid = a.id and voteresult = 0) AS negTotal	from artifact a where a.id = :artifactId", nativeQuery = true)
	List<Object[]> findArtifactWithCount(@Param("artifactId") Long artifactId);	
}
