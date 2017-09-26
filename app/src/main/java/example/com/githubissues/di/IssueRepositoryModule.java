package example.com.githubissues.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;
import example.com.githubissues.repositories.api.GithubApiService;
import example.com.githubissues.repositories.database.IssueDao;
import example.com.githubissues.repositories.database.IssueDb;


@Module
    public class IssueRepositoryModule {

        @Provides
        @Singleton
        public IssueRepositoryImpl provideIssueRepository(IssueDao issueDao, IssueDb issuedb, GithubApiService mApiService) {
            return new IssueRepositoryImpl(issueDao,issuedb,mApiService);
        }


    }

