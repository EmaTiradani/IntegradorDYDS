package integracion;

import model.SearchResult;
import model.WikipediaSearch;

import java.io.IOException;
import java.util.ArrayList;

public class WikiSearchStub extends WikipediaSearch {

    public ArrayList<SearchResult> search(String title) throws IOException {

        ArrayList<SearchResult> results = new ArrayList<>();
        //SearchResult result1 = new SearchResult("Default","1", "Default");

        switch(title){
            case "Pizza":{
                SearchResult result1 = new SearchResult("First","1", "Snippet");
                SearchResult result2 = new SearchResult("Second","2", "Snippet");
                SearchResult result3 = new SearchResult("Third","3", "Snippet");
                results.add(result1);
                results.add(result2);
                results.add(result3);
            }
            case "Test full article":{
                if(enableSearchFullArticle){
                    SearchResult result1 = new SearchResult("Full article","1", "This is very long");
                    results.add(result1);
                }else{
                    SearchResult result2 = new SearchResult("Not Full article","1", "This is very short");
                    results.add(result2);
                }

            }
        }


        return results;
    }

    public String getExtract(SearchResult searchResult){

        if(enableSearchFullArticle){
            return "Full article";
        }else{
            return "Only intro";
        }
    }




}
