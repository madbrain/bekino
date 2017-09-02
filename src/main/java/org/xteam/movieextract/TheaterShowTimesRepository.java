package org.xteam.movieextract;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.xteam.movieextract.model.TheaterShowTimes;

public interface TheaterShowTimesRepository extends MongoRepository<TheaterShowTimes, String>{

	TheaterShowTimes findByTheaterCodeAndDate(String code, String date);

}
