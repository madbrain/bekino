package org.xteam.movieextract.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class TheaterShowTimes {

	@Id
	private String id;
	
	private String theaterCode;
	
	private String date;
	
	private List<FilmShowTimes> films = new ArrayList<FilmShowTimes>();

	public TheaterShowTimes(String theaterCode, String date) {
		this.theaterCode = theaterCode;
		this.date = date;
	}

	public String getTheaterCode() {
		return theaterCode;
	}

	public void setTheaterCode(String theaterCode) {
		this.theaterCode = theaterCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<FilmShowTimes> getFilms() {
		return films;
	}
	
}
