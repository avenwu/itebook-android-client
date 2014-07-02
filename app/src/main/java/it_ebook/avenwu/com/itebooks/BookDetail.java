package it_ebook.avenwu.com.itebooks;

import android.os.Parcel;

public class BookDetail extends Book implements android.os.Parcelable {

    private String Error;
    private Double Time;
    private String Author;
    private String ISBN;
    private String Year;
    private String Page;
    private String Publisher;

    private String Download;

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


    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        this.ISBN = iSBN;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String page) {
        this.Page = page;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        this.Publisher = publisher;
    }

    public String getDownload() {
        return Download;
    }

    public void setDownload(String download) {
        this.Download = download;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.Error);
        dest.writeValue(this.Time);
        dest.writeString(this.Author);
        dest.writeString(this.ISBN);
        dest.writeString(this.Year);
        dest.writeString(this.Page);
        dest.writeString(this.Publisher);
        dest.writeString(this.Download);
    }

    public BookDetail() {
    }

    protected BookDetail(Parcel in) {
        super(in);
        this.Error = in.readString();
        this.Time = (Double) in.readValue(Double.class.getClassLoader());
        this.Author = in.readString();
        this.ISBN = in.readString();
        this.Year = in.readString();
        this.Page = in.readString();
        this.Publisher = in.readString();
        this.Download = in.readString();
    }

    public static final Creator<BookDetail> CREATOR = new Creator<BookDetail>() {
        public BookDetail createFromParcel(Parcel source) {
            return new BookDetail(source);
        }

        public BookDetail[] newArray(int size) {
            return new BookDetail[size];
        }
    };
}