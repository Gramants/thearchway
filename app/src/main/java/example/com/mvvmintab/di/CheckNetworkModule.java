package example.com.mvvmintab.di;

import android.content.Context;
import android.net.ConnectivityManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.mvvmintab.repositories.api.checknetwork.CheckNetwork;
import example.com.mvvmintab.repositories.api.checknetwork.CheckNetworkImpl;

@Module
public class CheckNetworkModule {

    Context context;

    public CheckNetworkModule(Context context) {
        this.context = context;
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
