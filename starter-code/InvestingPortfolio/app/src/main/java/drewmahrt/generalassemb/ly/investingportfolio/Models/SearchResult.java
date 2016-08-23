package drewmahrt.generalassemb.ly.investingportfolio.Models;

import java.util.ArrayList;

/**
 * Created by davidstone on 8/22/16.
 */
public class SearchResult {

    private ArrayList<StockSymbol> results;

    public ArrayList<StockSymbol> getResults() {
        return results;
    }

    public void setResults(ArrayList<StockSymbol> results) {
        this.results = results;
    }
}
