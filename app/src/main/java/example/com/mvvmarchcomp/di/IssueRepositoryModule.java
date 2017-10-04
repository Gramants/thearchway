package example.com.mvvmarchcomp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.mvvmarchcomp.repositories.IssueRepository;
import example.com.mvvmarchcomp.repositories.IssueRepositoryImpl;
import example.com.mvvmarchcomp.repositories.api.GithubApiService;
import example.com.mvvmarchcomp.repositories.database.IssueDao;
import example.com.mvvmarchcomp.repositories.database.IssueDb;


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

