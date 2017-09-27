package example.com.githubissues.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;
import example.com.githubissues.repositories.api.GithubApiService;
import example.com.githubissues.repositories.database.IssueDao;
import example.com.githubissues.repositories.database.IssueDb;
import retrofit2.Retrofit;


@Module
    public class IssueRepositoryModule {



        @Provides
        @Singleton
        public IssueRepositoryImpl provideIssueRepository(IssueDao issueDao, IssueDb issuedb, GithubApiService mApiService) {
            return new IssueRepositoryImpl(issueDao,issuedb,mApiService);
        }

        @Provides
        @Singleton
        public IssueRepository provideRepository(IssueRepositoryImpl issuerepository) {
            return  issuerepository;
        }


    }

