/** 
 * This is default SQL file for spring-boot to load at the beginning of application.
 * 
 * Current database uses H2 in memory.
 *  
 * Sequence is created for each entity. TABLE auto Id generation should not be used. 
 * The initial value of 100 and increment of 50 matches the JPA 'initialValue = 100 allocationSize=50' in the java data model.
 * 
 */

--- SEQUENCE ---
CREATE SEQUENCE IF NOT EXISTS SEQUENCE_USER START WITH 100 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS SEQUENCE_ARTIFACT START WITH 100 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS SEQUENCE_COURT START WITH 100 INCREMENT BY 50;

--- TABLE ---
DROP TABLE IF EXISTS court;
CREATE TABLE court(
	id INT NOT NULL,
	name VARCHAR(100) NOT NULL, 
	description VARCHAR(255) NOT NULL,
	category INT NOT NULL,
	type INT NOT NULL,
	PRIMARY KEY (id),
);

DROP TABLE IF EXISTS user;
CREATE TABLE user(
	id INT NOT NULL,
	username VARCHAR(80) NOT NULL UNIQUE, 
	password VARCHAR(80) NOT NULL,
	firstName VARCHAR(80),
	lastName VARCHAR(80),
	email VARCHAR(50),
	providerType INT,
	emailVerified INT default 0,
	active INT default 1,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS courtUser;
CREATE TABLE courtUser(
	courtId INT NOT NULL,
	userId INT NOT NULL,
	userCourtName VARCHAR(100),
	PRIMARY KEY (courtId, userid),
    CONSTRAINT FK_CourtUser_Court FOREIGN KEY (courtId) REFERENCES court(id),
    CONSTRAINT FK_CourtUser_User FOREIGN KEY (userId) REFERENCES user(id)
);

DROP TABLE IF EXISTS artifact;
CREATE TABLE artifact(
	id INT NOT NULL,
	title VARCHAR(255),
	description VARCHAR(255), 
	courtId INT,
	ownerId INT,
	status INT default 0,
	totalPos INT default 0,
	totalNeg INT default 0,
	PRIMARY KEY (id),
    CONSTRAINT FK_Artifact_Court FOREIGN KEY (courtId) REFERENCES court(id),
    CONSTRAINT FK_Artifact_User FOREIGN KEY (ownerId) REFERENCES user(id)
);

DROP TABLE IF EXISTS userArtifact;
CREATE TABLE userArtifact(
	userId INT NOT NULL,
	artifactId INT NOT NULL,
	voteResult INT,
	PRIMARY KEY (userid, artifactId),
    CONSTRAINT FK_UserArtifact_User FOREIGN KEY (userId) REFERENCES user(id),
    CONSTRAINT FK_UserArtifact_Artifact FOREIGN KEY (artifactId) REFERENCES artifact(id)
);

