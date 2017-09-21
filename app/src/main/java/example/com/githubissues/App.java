package example.com.githubissues;

import android.app.Application;
import android.arch.persistence.room.Room;

import example.com.githubissues.repositories.database.IssueDao;
import example.com.githubissues.repositories.database.IssueDb;

public class App extends Application {

    public static App INSTANCE;
    private static final String DATABASE_NAME = "MyDatabase";


    private IssueDb database;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // create database
        database = Room.databaseBuilder(getApplicationContext(), IssueDb.class, DATABASE_NAME)
                .build();

        INSTANCE = this;
    }

    public IssueDb getDB() {
        return database;
    }


}