package org.xteam.movieextract;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.xteam.movieextract.extract.ExtractAllocineMoviesImpl;
import org.xteam.movieextract.model.FilmShowTimes;
import org.xteam.movieextract.model.TheaterShowTimes;

public class TestExtractAllocineMovies {

	@Test
	public void testExtract() throws IOException {
		DocumentFetcher documentFetcher = Mockito.mock(DocumentFetcher.class);
		TheaterShowTimesRepository theaterShowTimesRepository = Mockito.mock(TheaterShowTimesRepository.class);
		
		Mockito.when(documentFetcher.fetch("P3757")).thenReturn(
				Jsoup.parse(getClass().getResourceAsStream("/P3757.html"), "utf-8", "http://www.allocine.fr/seance/"));
		
		ExtractAllocineMovies extractAllocineMovies = new ExtractAllocineMoviesImpl(documentFetcher, theaterShowTimesRepository);
		
		TheaterShowTimes result = extractAllocineMovies.extract("P3757", "2017-09-01");
		
		Assert.assertEquals("P3757", result.getTheaterCode());
		Assert.assertEquals("2017-09-01", result.getDate());
		Assert.assertEquals(11, result.getFilms().size());
		FilmShowTimes film = result.getFilms().get(0);
		Assert.assertEquals("Le Grand Méchant Renard et autres contes", film.getTitle());
		Assert.assertEquals(1, film.getShowTimes().size());
		Assert.assertEquals("10:45", film.getShowTimes().get(0).getStartTime());
		
		ArgumentCaptor<TheaterShowTimes> showTimesCaptor = ArgumentCaptor.forClass(TheaterShowTimes.class);
		Mockito.verify(theaterShowTimesRepository, Mockito.times(7)).save(showTimesCaptor.capture());
		
		result = showTimesCaptor.getAllValues().get(4);
		Assert.assertEquals("P3757", result.getTheaterCode());
		Assert.assertEquals("2017-09-02", result.getDate());
		Assert.assertEquals(13, result.getFilms().size());
		film = result.getFilms().get(1);
		Assert.assertEquals("120 battements par minute", film.getTitle());
		Assert.assertEquals(5, film.getShowTimes().size());
		Assert.assertEquals("10:55", film.getShowTimes().get(0).getStartTime());
	}

}
