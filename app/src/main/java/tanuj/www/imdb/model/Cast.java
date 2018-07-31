package tanuj.www.imdb.model;

import java.io.Serializable;

public class Cast implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String character;
	private String profile_path;
	private String job;
	private String key;

	public Cast() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getProfilePath() {
		return profile_path;
	}

	public void setProfilePath(String profile_path) {
		this.profile_path = profile_path;
	}

}