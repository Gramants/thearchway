package example.com.mvvmintab.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.mvvmintab.Config;
import example.com.mvvmintab.repositories.database.ContributorDao;
import example.com.mvvmintab.repositories.database.IssueDao;
import example.com.mvvmintab.repositories.database.ProjectDb;

@Module
public class DatabaseModule {

    private static final String DATABASE = "database_name";

    @Provides
    @Named(DATABASE)
    String provideDatabaseName() {
        return Config.DATABASE_NAME;
    }

    @Provides
    @Singleton
    ProjectDb provideIssueDb(Context context, @Named(DATABASE) String databaseName) {
        return Room.databaseBuilder(context, ProjectDb.class, databaseName).build();
    }

    @Provides
    @Singleton
    IssueDao provideIssueDao(ProjectDb projectDb) {
        return projectDb.issueDao();
    }

    @Provides
    @Singleton
    ContributorDao provideContributorDao(ProjectDb projectDb) {return projectDb.contributorDao();}
}
