package org.xteam.movieextract;

import java.util.List;

import org.xteam.movieextract.model.FilmShowTimes;
import org.xteam.movieextract.model.Theater;
import org.xteam.movieextract.model.TheaterShowTimes;

public class TheaterWithTimes {

	private String name;
	private String code;
	private String date;
	private List<FilmShowTimes> films;

	public TheaterWithTimes(Theater theater, TheaterShowTimes theaterShowTimes) {
		this.name = theater.getName();
		this.code = theater.getAllocineCode();
		if (theaterShowTimes != null) {
			this.date = theaterShowTimes.getDate();
			this.films = theaterShowTimes.getFilms();
		}
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getDate() {
		return date;
	}

	public List<FilmShowTimes> getFilms() {
		return films;
	}

}
