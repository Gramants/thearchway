package example.com.mvvmintab.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import example.com.mvvmintab.App;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.repositories.ContributorRepository;
import example.com.mvvmintab.repositories.IssueRepository;


public class InterFragmentsViewModel extends AndroidViewModel {

    private MediatorLiveData<IssueDataModel> mDbResponse;
    final MutableLiveData<IssueDataModel> liveDataShowItemBody= new MutableLiveData<>();
    @Inject
    IssueRepository mIssueRepository;

    @Inject
    ContributorRepository mContributorRepository;

    public InterFragmentsViewModel(Application application) {
        super(application);
        mDbResponse = new MediatorLiveData<>();
        ((App) application).getAppRepositoryComponent().inject(this);

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

        return mDbResponse;
    }



    public MutableLiveData<IssueDataModel> showIssueBodyContent() {
        return liveDataShowItemBody;
    }
    public void showItemBody(IssueDataModel issueDataModel) {
        liveDataShowItemBody.setValue(issueDataModel);
    }

}