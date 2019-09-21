package com.facecourt.webapp.persist;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.facecourt.webapp.model.UserArtifact;
import com.facecourt.webapp.model.UserArtifactKey;

public interface UserArtifactDao extends JpaRepository<UserArtifact, UserArtifactKey> {

	Set<UserArtifact> findByUserArtifactKeyUserId(Long userId);

	Set<UserArtifact> findByUserArtifactKeyArtifactId(Long artifactId);

	UserArtifact findByUserArtifactKey(UserArtifactKey userArtifactKey);

	@Query(value = "SELECT " 
			+ "    ua.voteResult, COUNT(*) as total " 
			+ "FROM " 
			+ "    UserArtifact ua "
			+ "WHERE ua.artifactId = :artifactId " 
			+ "GROUP BY " 
			+ "    ua.voteResult", nativeQuery = true)
	List<Object[]> findArtifactVoteNum(@Param("artifactId") Long artifactId);
	
	@Modifying
    @Transactional
	@Query(value = "delete from userartifact where artifactId = :artifactId", nativeQuery = true)
	public void deleteVotesByArtifactId(@Param("artifactId") Long artifactId);

}
