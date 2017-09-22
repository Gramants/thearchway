package example.com.githubissues.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "Issues")
public class Issue  {

    //@PrimaryKey(autoGenerate = true)
    //private int id;

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("repository_url")
    @Expose
    private String repositoryUrl;

    @PrimaryKey
    @SerializedName("number")
    @ColumnInfo(name = "id")
    @Expose
    private Integer number;
    @SerializedName("title")
    @Expose
    private String title;
    //@SerializedName("user")
    //@Expose
    //private User user;

    @SerializedName("state")
    @Expose
    private String state;
    @Expose
    private String createdAt;
    @SerializedName("body")
    @Expose
    private String body;

    public Issue(String url, String repositoryUrl, Integer number, String title, String state, String createdAt, String body) {
        this.url = url;
        this.repositoryUrl = repositoryUrl;
        this.number = number;
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //public int getId() {
    //    return id;
    //}

    //public void setId(int id) {
    //     this.id = id;
    //}
    //public User getUser() {
    //    return user;
    //}

    //public void setUser(User user) {
    //    this.user = user;
    //}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}