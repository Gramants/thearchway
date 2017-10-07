package example.com.mvvmintab.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import example.com.mvvmintab.App;
import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.repositories.ContributorRepository;
import example.com.mvvmintab.repositories.IssueRepository;
import example.com.mvvmintab.repositories.preferences.PersistentStorageProxy;


public class RootViewModel extends AndroidViewModel {

    //https://stackoverflow.com/questions/44270577/android-lifecycle-library-viewmodel-using-dagger-2

    private MediatorLiveData<List<IssueDataModel>> mApiIssueResponse;
    private MediatorLiveData<List<ContributorDataModel>> mApiContributorResponse;

    final MutableLiveData<String> livedatasavedstring = new MutableLiveData<>();

    @Inject
    IssueRepository mIssueRepository;

    @Inject
    ContributorRepository mContributorRepository;

    @Inject
    PersistentStorageProxy mPersistentStorageProxy;



    public RootViewModel(Application application) {
        super(application);
        mApiIssueResponse = new MediatorLiveData<>();
        mApiContributorResponse = new MediatorLiveData<>();
        ((App) application).getAppRepositoryComponent().inject(this);

    }


    @NonNull
    public LiveData<List<IssueDataModel>> getApiIssueResponse() {
        return mApiIssueResponse;
    }
    @NonNull
    public LiveData<List<ContributorDataModel>> getApiContributorResponse() {return mApiContributorResponse;}


    public LiveData<List<IssueDataModel>> loadIssues(@NonNull String user, String repo, Boolean forceremote) {
        mApiIssueResponse.addSource(
                mIssueRepository.getIssues(user, repo, forceremote),
                apiIssueResponse -> mApiIssueResponse.setValue(apiIssueResponse)
        );
        return mApiIssueResponse;
    }

    public LiveData<List<ContributorDataModel>> loadContributor(@NonNull String user, String repo, Boolean forceremote) {
        mApiIssueResponse.addSource(
                mContributorRepository.getContributors(user, repo, forceremote),
                apiContributorResponse -> mApiContributorResponse.setValue(apiContributorResponse)
        );
        return mApiContributorResponse;
    }



    public void deleteRecordById(Integer id) {
        mIssueRepository.deleteRecordById(id);
    }

    public void saveSearchString(String searchstring) {
        mPersistentStorageProxy.setSearchString(searchstring);
    }

    public LiveData<String> getSearchString() {
        livedatasavedstring.setValue( mPersistentStorageProxy.getSearchString());
        return livedatasavedstring;
    }

}