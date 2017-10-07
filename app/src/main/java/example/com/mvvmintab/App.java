package example.com.mvvmintab;

import android.app.Application;

import example.com.mvvmintab.di.ApiServiceModule;
import example.com.mvvmintab.di.AppModule;


import example.com.mvvmintab.di.AppRepositoryModule;
import example.com.mvvmintab.di.DaggerAppRepositoryComponent;
import example.com.mvvmintab.di.DatabaseModule;
import example.com.mvvmintab.di.AppRepositoryComponent;
import example.com.mvvmintab.di.PreferencesModule;

public class App extends Application {

    private AppRepositoryComponent appRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencies();
    }

    private void initializeDependencies() {

        appRepositoryComponent = DaggerAppRepositoryComponent.builder()
                .appModule(new AppModule(this))
                .preferencesModule(new PreferencesModule(this))
                .apiServiceModule(new ApiServiceModule())
                .databaseModule(new DatabaseModule())
                .appRepositoryModule(new AppRepositoryModule())
                .build();

    }

    public AppRepositoryComponent getAppRepositoryComponent() {return appRepositoryComponent;}

}