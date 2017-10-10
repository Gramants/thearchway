package example.com.mvvmintab.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.mvvmintab.repositories.api.checknetwork.CheckNetwork;
import example.com.mvvmintab.repositories.api.checknetwork.CheckNetworkImpl;
import example.com.mvvmintab.repositories.preferences.PersistentStorageProxy;
import example.com.mvvmintab.repositories.preferences.PersistentStorageProxyImpl;

/**
 * Created by Stefano on 29/09/2017.
 */
@Module
public class CheckNetworkModule {

    Context context;

    public CheckNetworkModule(Context context) {
        this.context=context;
    }

    @Singleton
    @Provides
    ConnectivityManager provideConnectivityManager(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

    }

    @Singleton
    @Provides
    CheckNetwork provideCheckNetworkImpl(ConnectivityManager connectivitymanager) {
        return new CheckNetworkImpl(connectivitymanager);
    }

}
