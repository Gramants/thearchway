package example.com.mvvmintab.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.mvvmintab.viewmodels.InterFragmentsViewModel;
import example.com.mvvmintab.viewmodels.RootViewModel;

@Singleton
    @Component(modules = { AppRepositoryModule.class, AppModule.class, ApiServiceModule.class, DatabaseModule.class,PreferencesModule.class,CheckNetworkModule.class})
    public interface AppRepositoryComponent {
        void inject(InterFragmentsViewModel interFragmentsViewModel);
        void inject(RootViewModel rootViewModel);
}

