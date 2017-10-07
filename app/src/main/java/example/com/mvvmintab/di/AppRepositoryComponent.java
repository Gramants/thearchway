package example.com.mvvmintab.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.mvvmintab.viewmodels.DetailViewModel;
import example.com.mvvmintab.viewmodels.ListIssuesViewModel;

@Singleton
    @Component(modules = { AppRepositoryModule.class, AppModule.class, ApiServiceModule.class, DatabaseModule.class,PreferencesModule.class})
    public interface AppRepositoryComponent {
        void inject(DetailViewModel detailViewModel);
        void inject(ListIssuesViewModel listIssuesViewModel);
}

