package com.facecourt.webapp.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facecourt.webapp.model.Court;

/**
 * Court DAO
 * 
 * @author suns
 *
 */
public interface CourtDao extends JpaRepository<Court, Long> {

}
