/** 
 * This is default SQL file for spring-boot to load at the beginning of application.
 * 
 * Current database uses H2 in memory. 
 */

DROP TABLE IF EXISTS court;
CREATE TABLE court(
	id INT,
	name VARCHAR(100) NOT NULL, 
	description VARCHAR(255) NOT NULL,
	category INT NOT NULL,
	type INT NOT NULL,
	PRIMARY KEY (id),
);

DROP TABLE IF EXISTS user;
CREATE TABLE user(
	id INT auto_increment,
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
	id INT auto_increment,
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


