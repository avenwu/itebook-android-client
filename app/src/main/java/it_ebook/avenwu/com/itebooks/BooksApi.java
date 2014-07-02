package it_ebook.avenwu.com.itebooks;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * http://it-ebooks-api.info/
 * Created by Chaobin on 2014/7/1.
 */
public interface BooksApi {

    //Search query (Note: 50 characters maximum)
    @GET("/search/{query}")
    BooksResult queryBooks(@Path("query") String query);

    //The page number of results (Note: 10 results per page)
    @GET("/search/{query}/page/{number}")
    BooksResult queryBooksonPage(@Path("query") String query, @Path("number") int number);

    @GET("/book/{id}")
    BookDetail getBookById(@Path("id") String id);
}
