package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.wikiAPI.WikipediaFullPageAPI;
import model.wikiAPI.WikipediaPageAPI;
import model.wikiAPI.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.*;

public class WikipediaSearch implements WikiSearch{

    protected boolean enableSearchFullArticle = false;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
    WikipediaFullPageAPI pageAPI = retrofit.create(WikipediaFullPageAPI.class);
    WikipediaPageAPI fullPageAPI = retrofit.create(WikipediaPageAPI.class);

    public void toggleFullArticle(boolean fullArticle){
        enableSearchFullArticle = fullArticle;
    }

    public boolean getArticleMode() {
        return enableSearchFullArticle;
    }

    public ArrayList<SearchResult> search(String title) throws IOException {

        ArrayList<SearchResult> results = new ArrayList<>();
        Response<String> callForSearchResponse;
        callForSearchResponse = searchAPI.searchForTerm(title + " articletopic:\"food-and-drink\"").execute();

        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        JsonArray jsonResults = query.get("search").getAsJsonArray();

        for (JsonElement je : jsonResults) {
            JsonObject searchResult = je.getAsJsonObject();
            String searchResultTitle = searchResult.get("title").getAsString();
            String searchResultPageId = searchResult.get("pageid").getAsString();
            String searchResultSnippet = searchResult.get("snippet").getAsString();

            SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            results.add(sr);
        }

        return results;
    }

    public String getExtract(SearchResult searchResult){
        String extract = "";
        Response<String> callForPageResponse = null;
        try {
            if(enableSearchFullArticle){
                callForPageResponse = pageAPI.getExtractByPageID(searchResult.pageID).execute();
            }else{
                callForPageResponse = fullPageAPI.getExtractByPageID(searchResult.pageID).execute();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query2 = jobj2.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");
        if (searchResultExtract2 == null) {
            extract = "No Results";
        } else {
            extract = "<h1>" + searchResult.title + "</h1>";
            extract += searchResultExtract2.getAsString().replace("\\n", "\n");

            if(enableSearchFullArticle){
                extract+="\n" + "<a href=https://en.wikipedia.org/?curid=" + searchResult.pageID +">View Full Article</a>";
            }

        }
        return extract;
    }



}