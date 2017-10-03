package example.com.githubissues.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import example.com.githubissues.App;
import example.com.githubissues.di.ApiServiceModule;
import example.com.githubissues.di.DaggerIssueRepositoryComponent;
import example.com.githubissues.di.DatabaseModule;
import example.com.githubissues.di.IssueRepositoryModule;
import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.repositories.IssueRepository;
import example.com.githubissues.repositories.IssueRepositoryImpl;


public class DetailViewModel extends AndroidViewModel {

    private MediatorLiveData<IssueDataModel> mDbResponse;

    @Inject
    IssueRepository mIssueRepository;

    public DetailViewModel(Application application) {
        super(application);
        mDbResponse = new MediatorLiveData<>();
        ((App) application).getIssueRepositoryComponent().inject(this);
    }


    @NonNull
    public LiveData<IssueDataModel> getdbResponse() {
        return mDbResponse;
    }


    public LiveData<IssueDataModel> loadIssue(int id) {
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