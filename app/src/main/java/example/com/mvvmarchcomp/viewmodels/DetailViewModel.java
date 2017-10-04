package example.com.mvvmarchcomp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import example.com.mvvmarchcomp.App;
import example.com.mvvmarchcomp.entities.IssueDataModel;
import example.com.mvvmarchcomp.repositories.IssueRepository;


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