package integracion;

import model.SearchResult;
import model.WikiSearch;

import java.io.IOException;
import java.util.ArrayList;

public class WikiSearchStub extends WikiSearch {
    public ArrayList<SearchResult> search(String title) throws IOException {
        ArrayList<SearchResult> results = new ArrayList<>();
        SearchResult result1 = new SearchResult("First","1", "Snippet");
        SearchResult result2 = new SearchResult("Second","2", "Snippet");
        SearchResult result3 = new SearchResult("Third","3", "Snippet");
        results.add(result1);
        results.add(result2);
        results.add(result3);
        return results;
    }
}
