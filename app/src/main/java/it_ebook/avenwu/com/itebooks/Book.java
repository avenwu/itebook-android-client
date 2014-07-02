package it_ebook.avenwu.com.itebooks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Book implements Parcelable {

    private String ID;
    private String Title;
    private String Description;
    private String Image;
    private String isbn;
    private String SubTitle;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        this.ID = iD;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        this.SubTitle = subTitle;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.Title);
        dest.writeString(this.Description);
        dest.writeString(this.Image);
        dest.writeString(this.isbn);
        dest.writeString(this.SubTitle);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.ID = in.readString();
        this.Title = in.readString();
        this.Description = in.readString();
        this.Image = in.readString();
        this.isbn = in.readString();
        this.SubTitle = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}