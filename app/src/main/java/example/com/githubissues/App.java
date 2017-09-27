package example.com.githubissues;

import android.app.Application;
import android.arch.persistence.room.Room;

import example.com.githubissues.di.ApiServiceModule;
import example.com.githubissues.di.AppModule;


import example.com.githubissues.di.DaggerIssueRepositoryComponent;
import example.com.githubissues.di.DatabaseModule;
import example.com.githubissues.di.IssueRepositoryComponent;
import example.com.githubissues.di.IssueRepositoryModule;
import example.com.githubissues.repositories.database.IssueDao;
import example.com.githubissues.repositories.database.IssueDb;

public class App extends Application {

    private IssueRepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencies();
    }

    private void initializeDependencies() {
        repositoryComponent = DaggerIssueRepositoryComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .databaseModule(new DatabaseModule())
                .issueRepositoryModule(new IssueRepositoryModule())
                .build();

    }

    public IssueRepositoryComponent getIssueRepositoryComponent() {
        return repositoryComponent;
    }
}