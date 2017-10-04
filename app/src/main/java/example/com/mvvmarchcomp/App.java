package example.com.mvvmarchcomp;

import android.app.Application;

import example.com.mvvmarchcomp.di.ApiServiceModule;
import example.com.mvvmarchcomp.di.AppModule;


import example.com.mvvmarchcomp.di.DaggerIssueRepositoryComponent;
import example.com.mvvmarchcomp.di.DatabaseModule;
import example.com.mvvmarchcomp.di.IssueRepositoryComponent;
import example.com.mvvmarchcomp.di.IssueRepositoryModule;
import example.com.mvvmarchcomp.di.PreferencesModule;

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
                .preferencesModule(new PreferencesModule(this))
                .apiServiceModule(new ApiServiceModule())
                .databaseModule(new DatabaseModule())
                .issueRepositoryModule(new IssueRepositoryModule())
                .build();

    }

    public IssueRepositoryComponent getIssueRepositoryComponent() {
        return repositoryComponent;
    }
}