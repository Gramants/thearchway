package example.com.githubissues.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import example.com.githubissues.entities.ApiResponse;
import example.com.githubissues.entities.Issue;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;


public class ListIssuesViewModel extends ViewModel {

    private MediatorLiveData<List<Issue>> mApiResponse;
    private IssueRepository mIssueRepository;

    public ListIssuesViewModel() {
        mApiResponse = new MediatorLiveData<>();
        mIssueRepository = new IssueRepositoryImpl();
    }

    @NonNull
    public LiveData<List<Issue>> getApiResponse() {
        return mApiResponse;
    }


    public LiveData<List<Issue>> loadIssues(@NonNull String user, String repo,Boolean forceremote) {
       // https://stackoverflow.com/questions/45679896/android-mediatorlivedata-observer
        mApiResponse.addSource(
                mIssueRepository.getIssues(user, repo,forceremote),
                apiResponse -> mApiResponse.setValue(apiResponse)
        );



        return mApiResponse;
    }

}