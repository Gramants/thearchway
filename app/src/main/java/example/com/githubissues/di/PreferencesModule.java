package example.com.githubissues.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.githubissues.App;
import example.com.githubissues.repositories.database.IssueDb;
import example.com.githubissues.repositories.preferences.PersistentStorageProxy;
import example.com.githubissues.repositories.preferences.PersistentStorageProxyImpl;

/**
 * Created by Stefano on 29/09/2017.
 */
@Module
public class PreferencesModule {

    Context context;

    public PreferencesModule(Context context) {
        this.context=context;
    }

    @Singleton
    @Provides
    SharedPreferences providePreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    PersistentStorageProxy providePersistentStorageProxy(SharedPreferences preferences) {
        return new PersistentStorageProxyImpl(preferences);
    }

}
