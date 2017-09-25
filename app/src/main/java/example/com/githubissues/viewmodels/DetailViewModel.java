package example.com.githubissues.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import example.com.githubissues.entities.Issue;
import example.com.githubissues.entities.IssueLinearized;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;



public class DetailViewModel  extends ViewModel {

    private MediatorLiveData<IssueLinearized> mDbResponse;
    private IssueRepository mIssueRepository;

    public DetailViewModel() {
        mDbResponse = new MediatorLiveData<>();
        mIssueRepository = new IssueRepositoryImpl();
    }

    @NonNull
    public LiveData<IssueLinearized> getdbResponse() {
        return mDbResponse;
    }


    public LiveData<IssueLinearized> loadIssue(int id) {
        Log.e("STEFANO","Issue id: "+String.valueOf(id));
        // https://stackoverflow.com/questions/45679896/android-mediatorlivedata-observer
        mDbResponse.addSource(
                mIssueRepository.getIssueFromDb(id),
                dbResponse -> mDbResponse.setValue(dbResponse)
        );


        //https://github.com/florent37/NewAndroidArchitecture-Component-Github
        // databinding
        return mDbResponse;
    }
}