package example.com.mvvmintab.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.mvvmintab.repositories.preferences.PersistentStorageProxy;
import example.com.mvvmintab.repositories.preferences.PersistentStorageProxyImpl;

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
