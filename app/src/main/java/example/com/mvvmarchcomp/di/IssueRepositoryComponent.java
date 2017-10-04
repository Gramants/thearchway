package example.com.mvvmarchcomp.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.mvvmarchcomp.repositories.IssueRepositoryImpl;
import example.com.mvvmarchcomp.viewmodels.DetailViewModel;
import example.com.mvvmarchcomp.viewmodels.ListIssuesViewModel;

@Singleton
    @Component(modules = { IssueRepositoryModule.class, AppModule.class, ApiServiceModule.class, DatabaseModule.class,PreferencesModule.class})
    public interface IssueRepositoryComponent {
        IssueRepositoryImpl provideIssueRepository();
        void inject(DetailViewModel detailViewModel);
        void inject(ListIssuesViewModel listIssuesViewModel);
}

