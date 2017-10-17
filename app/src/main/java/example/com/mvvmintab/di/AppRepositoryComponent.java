package example.com.mvvmintab.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.mvvmintab.viewmodels.FragmentCommunicationViewModel;
import example.com.mvvmintab.viewmodels.RepositoryViewModel;
import example.com.mvvmintab.viewmodels.UtilityViewModel;

@Singleton
    @Component(modules = { AppRepositoryModule.class, AppModule.class, ApiServiceModule.class, DatabaseModule.class,PreferencesModule.class,CheckNetworkModule.class})
    public interface AppRepositoryComponent {
        void inject(FragmentCommunicationViewModel fragmentCommunicationViewModel);
        void inject(RepositoryViewModel repositoryViewModel);
        void inject(UtilityViewModel utilityViewModel);
}

