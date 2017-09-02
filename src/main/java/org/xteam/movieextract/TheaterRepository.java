package org.xteam.movieextract;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.xteam.movieextract.model.Theater;

public interface TheaterRepository extends MongoRepository<Theater, String> {

	Theater findByName(String name);

	Theater findByAllocineCode(String code);
}
