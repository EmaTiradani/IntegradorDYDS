package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.wikiAPI.WikipediaPageAPI;
import model.wikiAPI.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class WikiSearch {

    boolean enableSearchFullArticle = false;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
    WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

    //todo variable rancia
    String text = ""; //Last searched text! this variable is central for everything

    public ArrayList<SearchResult> search(String title){

        ArrayList<SearchResult> results = new ArrayList<>();
        try{
            Response<String> callForSearchResponse;
            callForSearchResponse = searchAPI.searchForTerm(title + " articletopic:\"food-and-drink\"").execute();

            System.out.println("JSON " + callForSearchResponse.body());

            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            //Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
            JsonArray jsonResults = query.get("search").getAsJsonArray();

            for (JsonElement je : jsonResults) {
                JsonObject searchResult = je.getAsJsonObject();
                String searchResultTitle = searchResult.get("title").getAsString();
                String searchResultPageId = searchResult.get("pageid").getAsString();
                String searchResultSnippet = searchResult.get("snippet").getAsString();

                SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
                results.add(sr);
            }
        }catch (IOException e1) {
            e1.printStackTrace();
        }
        return results;
    }

    public String getExtract(SearchResult searchResult){
        Response<String> callForPageResponse = null;
        try {
            callForPageResponse = pageAPI.getExtractByPageID(searchResult.pageID).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        //faltan unas cosas del gson

        JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query2 = jobj2.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");
        if (searchResultExtract2 == null) {
            text = "No Results";
        } else {
            text = "<h1>" + searchResult.title + "</h1>";
            text += searchResultExtract2.getAsString().replace("\\n", "\n");
            text = textToHtml(text);

            //TODO Falta algo?
            if(enableSearchFullArticle){
                text+="\n" + "<a href=https://en.wikipedia.org/?curid=" + searchResult.pageID +">View Full Article</a>";
            }

        }
        return text;
    }

    public static String textToHtml(String text) {

        StringBuilder builder = new StringBuilder();

        builder.append("<font face=\"arial\">");

        String fixedText = text
                .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..

        builder.append(fixedText);

        builder.append("</font>");

        return builder.toString();
    }

    public void toggleFullArticle(boolean fullArticle){
        enableSearchFullArticle = fullArticle;
    }
}