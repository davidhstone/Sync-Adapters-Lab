package drewmahrt.generalassemb.ly.investingportfolio;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.os.Handler;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;


import drewmahrt.generalassemb.ly.investingportfolio.Models.SearchResult;
import drewmahrt.generalassemb.ly.investingportfolio.Models.StockSymbol;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by davidstone on 8/22/16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "SyncAdapter";

    private ContentResolver mContentResolver;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        final ArrayList<String> symbols = MyDBHandler.getInstance(getContext()).getAllStockSymbols();

        if (symbols != null && !symbols.isEmpty()) {
            for (final String symbol : symbols) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://dev.markitondemand.com/Api/v2/Quote/json?symbol=" + symbol)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    Gson gson = new Gson();
                    if (response.isSuccessful()) {
                        final StockSymbol stockSymbol = gson.fromJson(response.body().string(), StockSymbol.class);
                        ContentValues values = new ContentValues();
                        values.put(StockPortfolioContract.Stocks.COLUMN_PRICE, stockSymbol.getLastPrice());
                        mContentResolver.update(StockPortfolioContract.Stocks.CONTENT_URI, values,
                                StockPortfolioContract.Stocks.COLUMN_STOCK_SYMBOL + " =?",
                                new String[]{stockSymbol.getSymbol()});
                        Log.i(TAG, "ARRAYLIST SIZE: " + symbols.size());
                        Log.i(TAG, "ONPERFORMSYNC: SYMBOL - " + stockSymbol.getSymbol());
                        Log.i(TAG, "ONPERFORMSYNC: PRICE - " + stockSymbol.getLastPrice());
                        Log.i(TAG, "ONPERFORMSYNC: TIME - " + stockSymbol.getTimestamp());
                        Runnable task = new Runnable() {
                            @Override
                            public void run() {
                                Calendar cal = Calendar.getInstance();
                                MainActivity.updateTime(cal.getTime().toString());
                            }
                        };
                        new Handler(Looper.getMainLooper()).post(task);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}