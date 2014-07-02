package it_ebook.avenwu.com.itebooks;

import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by Administrator on 2014/7/1.
 */
public class ApiServiceTest extends TestCase {
    private static final String TAG = ApiServiceTest.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testQueryBooks() {
        BooksResult result = ApiService.getService().queryBooks("Android");
        assertNotNull(result);
        assertNotNull(result.getBooks());
        for (Book book : result.getBooks()) {
            assertNotNull(book);
            Log.d(TAG, "id:" + book.getID());
            Log.d(TAG,"title:" + book.getTitle());
            Log.d(TAG,"subtitle" + book.getSubTitle());
            Log.d(TAG,"isbn" + book.getIsbn());
            Log.d(TAG,"images" + book.getImage());
            Log.d(TAG,"description" + book.getDescription());
        }
    }

    public void testGetBookById(){
        BookDetail detail = ApiService.getService().getBookById("2279690981");
        Assert.assertNotNull(detail);
        Assert.assertEquals("2279690981", detail.getID());
        Assert.assertEquals("Brett McLaughlin", detail.getAuthor());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
