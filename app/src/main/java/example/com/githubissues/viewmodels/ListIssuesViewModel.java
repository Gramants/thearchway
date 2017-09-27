package example.com.githubissues.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import example.com.githubissues.App;
import example.com.githubissues.di.ApiServiceModule;
import example.com.githubissues.di.AppModule;
import example.com.githubissues.di.DaggerIssueRepositoryComponent;
import example.com.githubissues.di.DatabaseModule;
import example.com.githubissues.di.IssueRepositoryComponent;
import example.com.githubissues.di.IssueRepositoryModule;
import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;


public class ListIssuesViewModel extends AndroidViewModel {

    //https://stackoverflow.com/questions/44270577/android-lifecycle-library-viewmodel-using-dagger-2

    private MediatorLiveData<List<IssueDataModel>> mApiResponse;

    @Inject
    IssueRepository mIssueRepository;

    public ListIssuesViewModel(Application application) {
        super(application);
        mApiResponse = new MediatorLiveData<>();
        ((App) application).getIssueRepositoryComponent().inject(this);

    }


    @NonNull
    public LiveData<List<IssueDataModel>> getApiResponse() {
        return mApiResponse;
    }


    public LiveData<List<IssueDataModel>> loadIssues(@NonNull String user, String repo, Boolean forceremote) {
        // https://stackoverflow.com/questions/45679896/android-mediatorlivedata-observer
        mApiResponse.addSource(
                mIssueRepository.getIssues(user, repo, forceremote),
                apiResponse -> mApiResponse.setValue(apiResponse)
        );


        //https://github.com/florent37/NewAndroidArchitecture-Component-Github
        // databinding
        return mApiResponse;
    }

    public void deleteRecordById(Integer id) {
        mIssueRepository.deleteRecordById(id);
    }
}