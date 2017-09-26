package example.com.githubissues.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;

@Singleton
    @Component(modules = { IssueRepositoryModule.class, AppModule.class, ApiServiceModule.class, DatabaseModule.class})
    public interface IssueRepositoryComponent {
        IssueRepositoryImpl provideIssueRepository();
    }

