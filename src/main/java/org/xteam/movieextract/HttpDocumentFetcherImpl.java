package org.xteam.movieextract;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class HttpDocumentFetcherImpl implements DocumentFetcher {

	@Override
	public Document fetch(String theaterCode) throws IOException {
		return Jsoup.parse(new URL(
				"http://www.allocine.fr/seance/salle_gen_csalle="+theaterCode+".html"), 5000);
	}

}
