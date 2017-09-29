package example.com.githubissues.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import example.com.githubissues.Config;


@Entity(tableName = Config.QUESTION_TABLE_NAME)
public class IssueDataModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "number")
    private Integer number;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "repositoryurl")
    private String repositoryUrl;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "userurl")
    private String userurl;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "createdat")
    private String createdAt;
    @ColumnInfo(name = "body")
    private String body;



    public IssueDataModel(Integer id, String url, String repositoryUrl, Integer number, String title, String state, String createdAt, String body, String username, String userurl) {
        this.id = id;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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