package example.com.githubissues.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;



public class DetailViewModel  extends ViewModel {

    private MediatorLiveData<IssueDataModel> mDbResponse;
    @Inject
    IssueRepository mIssueRepository;


    public DetailViewModel() {
        mDbResponse = new MediatorLiveData<>();

    }

    @NonNull
    public LiveData<IssueDataModel> getdbResponse() {
        return mDbResponse;
    }


    public LiveData<IssueDataModel> loadIssue(int id) {
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