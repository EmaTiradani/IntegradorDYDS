package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dyds.gourmetCatalog.fulllogic.SearchResult;
import dyds.gourmetCatalog.fulllogic.WikipediaPageAPI;
import dyds.gourmetCatalog.fulllogic.WikipediaSearchAPI;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CatalogModelImpl implements CatalogModel {

    //todo estas 2 variables estan re rancias, hay que ver como volarlas
    String selectedResultTitle = null;
    String lastSearchedText = "";
    String lastDeletedTitle = "";
    private DataBase dataBase;

    private ArrayList<CatalogModelListener> listeners = new ArrayList<>();

    public void setDataBase(DataBase dataBase){
        this.dataBase=dataBase;
    }

    @Override
    public String getSave(String title) {
        //comboBox1.addActionListener(actionEvent -> textPane2.setText(textToHtml(DataBase.getExtract(comboBox1.getSelectedItem().toString()))));
        return dataBase.getExtract(title);
    }

    @Override
    public boolean saveArticleChanges(String title, String body) {
        //DataBase.saveInfo(comboBox1.getSelectedItem().toString().replace("'", "`"), textPane2.getText());  //Dont forget the ' sql problem
        DataBase.saveInfo(title.replace("'", "`"), body);//Todo hacer que lo de comillas lo haga la database
        return true; //Que retorne falso si la database no lo pudo meter o algo asi.
    }

    @Override
    public boolean saveArticle(String title, String body) {
        /*if(text != ""){
            // save to DB  <o/
            dyds.gourmetCatalog.fulllogic.  //Dont forget the ' sql problem
            comboBox1.setModel(new DefaultComboBoxModel<Object>(dyds.gourmetCatalog.fulllogic.DataBase.getTitles().stream().sorted().toArray()));*/
        dyds.gourmetCatalog.fulllogic.DataBase.saveInfo(title.replace("'", "`"), body);
        notifySaveListener();
        return true;
    }

    @Override
    public boolean deleteArticle(String title) {

        return true;
    }


    public void addListener(CatalogModelListener listener){
        this.listeners.add(listener);
    }


    private void notifySearchListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSearchOnWiki();
        }
    }
    private void notifySelectSearchListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSelectSearchOption();
        }
    }
    private void notifySaveListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSaveLocally();
        }
    }
    private void notifySelectSaveListener(){
        for(CatalogModelListener listener: listeners){
            listener.didSelectSavedSearch();
        }
    }
    private void notifyDeleteListener(){
        for(CatalogModelListener listener: listeners){
            listener.didDeleteSave();
        }
    }

    //TODO sacar toda la parte de la busqueda en wiki hacia un utils o hacia una clase "WikiSearch".

    public void SearchOnWiki(Response<String> callForSearchResponse){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //todo ver que onda con esta cosa
        WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
        WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);

        //Response<String> callForSearchResponse;
        //try {

        //First, lets search for the term in Wikipedia
        //callForSearchResponse = searchAPI.searchForTerm(textField1.getText() + " articletopic:\"food-and-drink\"").execute();

        System.out.println("JSON " + callForSearchResponse.body());

        Gson gson = new Gson();
        JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
        JsonArray jsonResults = query.get("search").getAsJsonArray();

        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        for (JsonElement je : jsonResults) {
            JsonObject searchResult = je.getAsJsonObject();
            String searchResultTitle = searchResult.get("title").getAsString();
            String searchResultPageId = searchResult.get("pageid").getAsString();
            String searchResultSnippet = searchResult.get("snippet").getAsString();

            SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            searchOptionsMenu.add(sr);
            sr.addActionListener(actionEvent -> {
                try {
                    //This may take some time, dear user be patient in the meanwhile!
                    //setWorkingStatus();
                    Response<String> callForPageResponse = pageAPI.getExtractByPageID(sr.pageID).execute();

                    System.out.println("JSON " + callForPageResponse.body());
                    JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
                    JsonObject query2 = jobj2.get("query").getAsJsonObject();
                    JsonObject pages = query2.get("pages").getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
                    Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
                    JsonObject page = first.getValue().getAsJsonObject();
                    JsonElement searchResultExtract2 = page.get("extract");
                    if (searchResultExtract2 == null) {
                        lastSearchedText = "No Results";
                    } else {
                        lastSearchedText = "<h1>" + sr.title + "</h1>";
                        selectedResultTitle = sr.title;
                        lastSearchedText += searchResultExtract2.getAsString().replace("\\n", "\n");
                        lastSearchedText = textToHtml(lastSearchedText);

                        //Not yet...
                        //text+="\n" + "<a href=https://en.wikipedia.org/?curid=" + searchResultPageId +">View Full Article</a>";
                    }
                    //todo aca le tiene que decir al presentador que ya cambio los datos, asi actualiza los campos de la vista
                    notifySearchListener();
                        /*textPane1.setText(lastSearchedText);
                        textPane1.setCaretPosition(0);*/
                    //Back to edit time!
                    //setWatingStatus();
                } catch (Exception e12) {
                    System.out.println(e12.getMessage());
                }
            });
        }
        //searchOptionsMenu.show(textField1, textField1.getX(), textField1.getY());
        //} catch (IOException e1) { Esta parte eran las excepciones de IO, que el modelo no tiene nada que ver
        //    e1.printStackTrace();
        //}
    }

    public String getSearchResult(){
        return lastSearchedText;
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
}
