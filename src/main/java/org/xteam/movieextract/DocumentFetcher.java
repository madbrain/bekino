package org.xteam.movieextract;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface DocumentFetcher {

	Document fetch(String theaterCode) throws IOException;

}
