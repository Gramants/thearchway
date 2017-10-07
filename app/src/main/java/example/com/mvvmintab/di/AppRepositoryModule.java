package example.com.mvvmintab.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.mvvmintab.repositories.ContributorRepository;
import example.com.mvvmintab.repositories.ContributorRepositoryImpl;
import example.com.mvvmintab.repositories.IssueRepository;
import example.com.mvvmintab.repositories.IssueRepositoryImpl;
import example.com.mvvmintab.repositories.api.GithubApiService;
import example.com.mvvmintab.repositories.database.ContributorDao;
import example.com.mvvmintab.repositories.database.IssueDao;
import example.com.mvvmintab.repositories.database.ProjectDb;


@Module
public class AppRepositoryModule {


    @Provides
    @Singleton
    public IssueRepositoryImpl provideIssueRepositoryImpl(IssueDao issueDao, ProjectDb projectdb, GithubApiService mApiService) {
        return new IssueRepositoryImpl(issueDao, projectdb, mApiService);
    }

    @Provides
    @Singleton
    public IssueRepository provideIssueRepository(IssueRepositoryImpl issuerepository) {
        return issuerepository;
    }

    @Provides
    @Singleton
    public ContributorRepositoryImpl provideContributorRepositoryImpl(ContributorDao contributorDao, ProjectDb projectdb, GithubApiService mApiService) {
        return new ContributorRepositoryImpl(contributorDao, projectdb, mApiService);
    }

    @Provides
    @Singleton
    public ContributorRepository provideContributorRepository(ContributorRepositoryImpl contributorrepository) {
        return contributorrepository;
    }


}

