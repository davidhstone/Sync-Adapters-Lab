package drewmahrt.generalassemb.ly.investingportfolio.Models;

/**
 * Created by davidstone on 8/22/16.
 */
public class StockSymbol {

    String Symbol;
    String LastPrice;
    String Timestamp;

    public String getSymbol() {
        return Symbol;
    }

    public String getLastPrice() {
        return LastPrice;
    }

    public void setLastPrice(String lastPrice) {
        LastPrice = lastPrice;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }
}
