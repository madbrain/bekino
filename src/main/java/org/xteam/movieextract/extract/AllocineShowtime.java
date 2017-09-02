package org.xteam.movieextract.extract;

import java.time.ZonedDateTime;

public class AllocineShowtime {

    private ZonedDateTime showStart;
    private ZonedDateTime movieStart;
    private ZonedDateTime movieEnd;

    public ZonedDateTime getShowStart() {
        return showStart;
    }

    public void setShowStart(ZonedDateTime showStart) {
        this.showStart = showStart;
    }

    public ZonedDateTime getMovieStart() {
        return movieStart;
    }

    public void setMovieStart(ZonedDateTime movieStart) {
        this.movieStart = movieStart;
    }

    public ZonedDateTime getMovieEnd() {
        return movieEnd;
    }

    public void setMovieEnd(ZonedDateTime movieEnd) {
        this.movieEnd = movieEnd;
    }
}
