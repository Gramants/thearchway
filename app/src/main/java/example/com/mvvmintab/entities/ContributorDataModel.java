package example.com.mvvmintab.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import example.com.mvvmintab.Config;

/**
 * Created by Stefano on 07/10/2017.
 */
@Entity(tableName = Config.CONTRIBUTORS_TABLE_NAME)
public class ContributorDataModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "login")
    private String login;
    @ColumnInfo(name = "html_url")
    private String html_url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }


    public ContributorDataModel(int id, String login, String html_url) {

        this.id = id;
        this.login = login;
        this.html_url = html_url;
    }
}
