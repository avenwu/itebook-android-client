package it_ebook.avenwu.com.itebooks;

import retrofit.RestAdapter;

/**
 * Created by Administrator on 2014/7/1.
 */
public class ApiService {

    private static BooksApi booksApi;

    static {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://it-ebooks-api.info/v1")
                .build();
        booksApi = restAdapter.create(BooksApi.class);
    }

    public static BooksApi getService() {
        return booksApi;
    }
}
