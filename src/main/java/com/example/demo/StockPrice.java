package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class StockPrice {
    public static String stockPrice(String requestedTickerUpper) throws IOException {
        //DECLARES AND ALLOCATES SPACE TO ARRAYS FOR SCRAPED TICKER AND PRICES
        String[] scrapedTicker = new String[520];
        String[] scrapedPrice = new String[520];

        //INT I FOR THE FOR LOOP THAT FILLS THE ARRAYS
        int i = 0;

        //DECLARES stockInfo RETURN VARIABLE
        String stockInfo = "";

        //URL THAT IS BEING SCRAPED
        final String url = "https://www.slickcharts.com/sp500";

        //ASSIGNS HTML CODE OF URL TO A "DOCUMENT"
        final Document document = Jsoup.connect(url).get();

        //FOR LOOP TO FILL ARRAYS
        for (Element row : document.select("div.table-responsive tr")) {
            scrapedTicker[i] = row.select("td:nth-of-type(3)").text();
            scrapedPrice[i] = row.select("td:nth-of-type(5)").text();
            i++;

        }

        //FOR LOOP TO PARSE THROUGH ARRAY FOR REQUESTED TICKER AND PRICE
        for (int x = 0; x < scrapedTicker.length; x++){
            if (scrapedTicker[x] != null && scrapedTicker[x].equals(requestedTickerUpper)){
                stockInfo = scrapedTicker[x] + ", " + scrapedPrice[x];
            }
        }

        //RETURNS REQUESTED DATA
        return stockInfo;
    }
}
