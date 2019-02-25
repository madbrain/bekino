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
		
		TheaterShowTimes result = extractAllocineMovies.extract("P3757", "2019-02-23");
		
		Assert.assertEquals("P3757", result.getTheaterCode());
		Assert.assertEquals("2019-02-23", result.getDate());
		Assert.assertEquals(7, result.getFilms().size());
		FilmShowTimes film = result.getFilms().get(0);
		Assert.assertEquals("Tout ce qu'il me reste de la révolution", film.getTitle());
		Assert.assertEquals(1, film.getShowTimes().size());
		Assert.assertEquals("20:35", film.getShowTimes().get(0).getStartTime());
		
		ArgumentCaptor<TheaterShowTimes> showTimesCaptor = ArgumentCaptor.forClass(TheaterShowTimes.class);
		Mockito.verify(theaterShowTimesRepository, Mockito.times(13)).save(showTimesCaptor.capture());
		
		result = showTimesCaptor.getAllValues().get(4);
		Assert.assertEquals("P3757", result.getTheaterCode());
		Assert.assertEquals("2019-02-23", result.getDate());
		Assert.assertEquals(7, result.getFilms().size());
		film = result.getFilms().get(1);
		Assert.assertEquals("Euforia", film.getTitle());
		Assert.assertEquals(1, film.getShowTimes().size());
		Assert.assertEquals("20:40", film.getShowTimes().get(0).getStartTime());
	}

}
