package org.xteam.movieextract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xteam.movieextract.model.Theater;
import org.xteam.movieextract.model.TheaterShowTimes;

@RestController
public class TheaterController {

    @Autowired
	private TheaterRepository theaterRepository;
    
    @Autowired
    private TheaterShowTimesRepository theaterShowTimesRepository;
    
    @Autowired
    private ExtractAllocineMovies extractAllocineMovies;

    @RequestMapping("/theater")
    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }
    
    @RequestMapping("/theater/{code}")
    public TheaterWithTimes theater(@PathVariable("code") String code,
    		@RequestParam(value="date", required=false) String date) {
    	Theater theater = theaterRepository.findByAllocineCode(code);
    	
    	if (theater == null) {
    		return null;
    	}
    	
    	if (date == null) {
    		date = new SimpleDateFormat(ExtractAllocineMovies.DATE_PATTERN).format(
    			Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris")).getTime());
    	}
    	
    	TheaterShowTimes theaterShowTimes = theaterShowTimesRepository.findByTheaterCodeAndDate(
    			theater.getAllocineCode(), date);
    	
    	if (theaterShowTimes == null) {
    		theaterShowTimes = extractAllocineMovies.extract(theater.getAllocineCode(), date);
    	}
    	
    	return new TheaterWithTimes(theater, theaterShowTimes);
    }
}
