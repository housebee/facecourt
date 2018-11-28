package com.facecourt.webapp.persist;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facecourt.webapp.model.UserArtifact;
import com.facecourt.webapp.model.UserArtifactKey;

public interface UserArtifactDao extends JpaRepository<UserArtifact, UserArtifactKey> {

	Set<UserArtifact> findByUserArtifactKeyUserId(Long userId);

	Set<UserArtifact> findByUserArtifactKeyArtifactId(Long artifactId);

}
