package org.xteam.movieextract.model;

import java.util.ArrayList;
import java.util.List;

public class FilmShowTimes {

	private String title;
	private List<ShowTime> showTimes = new ArrayList<ShowTime>();

	public FilmShowTimes(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public List<ShowTime> getShowTimes() {
		return showTimes;
	}

}
