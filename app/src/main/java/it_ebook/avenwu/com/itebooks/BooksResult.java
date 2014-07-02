package it_ebook.avenwu.com.itebooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksResult {

    private String Error;
    private Double Time;
    private String Total;
    private Integer Page;
    private List<Book> Books = new ArrayList<Book>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        this.Error = error;
    }

    public Double getTime() {
        return Time;
    }

    public void setTime(Double time) {
        this.Time = time;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        this.Total = total;
    }

    public Integer getPage() {
        return Page;
    }

    public void setPage(Integer page) {
        this.Page = page;
    }

    public List<Book> getBooks() {
        return Books;
    }

    public void setBooks(List<Book> books) {
        this.Books = books;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}