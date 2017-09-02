package org.xteam.movieextract.extract;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;
import org.xteam.movieextract.DocumentFetcher;
import org.xteam.movieextract.ExtractAllocineMovies;
import org.xteam.movieextract.TheaterShowTimesRepository;
import org.xteam.movieextract.model.FilmShowTimes;
import org.xteam.movieextract.model.ShowTime;
import org.xteam.movieextract.model.TheaterShowTimes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExtractAllocineMoviesImpl implements ExtractAllocineMovies {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractAllocineMoviesImpl.class);

    private DocumentFetcher documentFetcher;

    private TheaterShowTimesRepository theaterShowTimesRepository;

    @Autowired
    public ExtractAllocineMoviesImpl(DocumentFetcher documentFetcher, TheaterShowTimesRepository theaterShowTimesRepository) {
        this.documentFetcher = documentFetcher;
        this.theaterShowTimesRepository = theaterShowTimesRepository;
    }

    @Override
    public TheaterShowTimes extract(String theaterCode, String date) {

        Map<String, TheaterShowTimes> resultMap = new HashMap<String, TheaterShowTimes>();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm")
                    .withZone(ZoneId.of("UTC"));
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            Document doc = documentFetcher.fetch(theaterCode);
            Elements element = doc.select("section.js-movie-list");
            AllocineData data = mapper.readValue(element.attr("data-movies-showtimes"), AllocineData.class);

            for (Map.Entry<String, Map<String, List<AllocineShowtimes>>> entry : data.getShowtimes().get(theaterCode).entrySet()) {
                String dayDate = entry.getKey();

                TheaterShowTimes theaterShowTimes = resultMap.get(dayDate);
                if (theaterShowTimes == null) {
                    resultMap.put(dayDate, theaterShowTimes = new TheaterShowTimes(theaterCode, dayDate));
                }

                for (Map.Entry<String, List<AllocineShowtimes>> e : entry.getValue().entrySet()) {
                    AllocineMovie film = data.getMovies().get(e.getKey());
					FilmShowTimes filmShowTimes = new FilmShowTimes(film.getTitle());
                    theaterShowTimes.getFilms().add(filmShowTimes);

                    for (AllocineShowtimes sts : e.getValue()) {
                        for (AllocineShowtime s : sts.getShowtimes()) {
                            filmShowTimes.getShowTimes().add(new ShowTime(
                                    formatter.format(s.getShowStart()),
                                    formatter.format(s.getMovieEnd())));
                        }
                    }
                }
            }

            for (TheaterShowTimes theaterShowTimes : resultMap.values()) {
                theaterShowTimesRepository.save(theaterShowTimes);
            }

        } catch (IOException e) {
            LOGGER.error("error while extracting theater " + theaterCode, e);
        }

        return resultMap.get(date);
    }

}
