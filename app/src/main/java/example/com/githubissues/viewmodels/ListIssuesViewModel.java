package example.com.githubissues.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;


public class ListIssuesViewModel extends ViewModel {

    private MediatorLiveData<List<IssueDataModel>> mApiResponse;

    @Inject
    IssueRepository mIssueRepository;

    public ListIssuesViewModel() {
        mApiResponse = new MediatorLiveData<>();
    }

    @NonNull
    public LiveData<List<IssueDataModel>> getApiResponse() {
        return mApiResponse;
    }


    public LiveData<List<IssueDataModel>> loadIssues(@NonNull String user, String repo, Boolean forceremote) {
       // https://stackoverflow.com/questions/45679896/android-mediatorlivedata-observer
        mApiResponse.addSource(
                mIssueRepository.getIssues(user, repo,forceremote),
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