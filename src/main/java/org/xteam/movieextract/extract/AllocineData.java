package org.xteam.movieextract.extract;

import java.util.List;
import java.util.Map;

public class AllocineData {
    private Map<String, AllocineMovie> movies;
    private Map<String, Map<String, Map<String, List<AllocineShowtimes>>>> showtimes;

    public Map<String, Map<String, Map<String, List<AllocineShowtimes>>>> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(Map<String, Map<String, Map<String, List<AllocineShowtimes>>>> showtimes) {
        this.showtimes = showtimes;
    }

    public Map<String, AllocineMovie> getMovies() {
        return movies;
    }

    public void setMovies(Map<String, AllocineMovie> movies) {
        this.movies = movies;
    }
}
