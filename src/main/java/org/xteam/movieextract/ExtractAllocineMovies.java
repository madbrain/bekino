package org.xteam.movieextract;

import org.xteam.movieextract.model.TheaterShowTimes;

public interface ExtractAllocineMovies {
	
	static final String DATE_PATTERN = "yyyy-MM-dd";

	TheaterShowTimes extract(String theaterCode, String date);

}
