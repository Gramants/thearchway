package example.com.mvvmarchcomp.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.mvvmarchcomp.Config;
import example.com.mvvmarchcomp.repositories.database.IssueDao;
import example.com.mvvmarchcomp.repositories.database.IssueDb;

/**
 * Created by Stefano on 26/09/2017.
 */
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
    IssueDb provideIssueDb(Context context, @Named(DATABASE) String databaseName) {
        return Room.databaseBuilder(context, IssueDb.class, databaseName).build();
    }

    @Provides
    @Singleton
    IssueDao provideIssueDao(IssueDb stackOverflowDb) {
        return stackOverflowDb.issueDao();
    }
}
