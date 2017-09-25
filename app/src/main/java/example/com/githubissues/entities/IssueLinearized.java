package example.com.githubissues.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "Issues")
public class IssueLinearized {



    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer number;


    private String url;

    private String repositoryUrl;


    private String title;


    private String username;

    private String userurl;

    private String state;

    private String createdAt;

    private String body;



    public IssueLinearized(String url, String repositoryUrl, Integer number, String title,String state, String createdAt, String body, String username, String userurl) {
        this.url = url;
        this.repositoryUrl = repositoryUrl;
        this.number = number;
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.body = body;
        this.username = username;
        this.userurl = userurl;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserurl() {
        return userurl;
    }

    public void setUserurl(String userurl) {
        this.userurl = userurl;
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