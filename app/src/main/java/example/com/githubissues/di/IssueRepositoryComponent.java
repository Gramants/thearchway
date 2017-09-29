package example.com.githubissues.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;
import example.com.githubissues.viewmodels.DetailViewModel;
import example.com.githubissues.viewmodels.ListIssuesViewModel;

@Singleton
    @Component(modules = { IssueRepositoryModule.class, AppModule.class, ApiServiceModule.class, DatabaseModule.class,PreferencesModule.class})
    public interface IssueRepositoryComponent {
        IssueRepositoryImpl provideIssueRepository();
        void inject(DetailViewModel detailViewModel);
        void inject(ListIssuesViewModel listIssuesViewModel);
}

