package example.com.mvvmintab.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import example.com.mvvmintab.App;
import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.NetworkErrorObject;
import example.com.mvvmintab.repositories.ContributorRepository;
import example.com.mvvmintab.repositories.IssueRepository;
import example.com.mvvmintab.repositories.api.checknetwork.CheckNetwork;
import example.com.mvvmintab.repositories.preferences.PersistentStorageProxy;


public class RootViewModel extends AndroidViewModel {

    private MediatorLiveData<List<IssueDataModel>> mApiIssueResponse;
    private MediatorLiveData<List<ContributorDataModel>> mApiContributorResponse;
    final MutableLiveData<Boolean> liveDataShowDialogIssueAndContributor = new MutableLiveData<>();
    final MutableLiveData<String> livedatasavedstring = new MutableLiveData<>();
    final MutableLiveData<String> livedatasnackbar = new MutableLiveData<>();
    final MutableLiveData<Boolean> liveDataIsInternetConnected = new MutableLiveData<>();
    private String searchstring;
    private Boolean fromremote;
    @Inject
    IssueRepository mIssueRepository;

    @Inject
    ContributorRepository mContributorRepository;

    @Inject
    PersistentStorageProxy mPersistentStorageProxy;

    @Inject
    CheckNetwork mCheckNetwork;


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


    public LiveData<NetworkErrorObject> getIssueNetworkErrorResponse() {
        return mIssueRepository.getNetworkError();
    }
    public LiveData<NetworkErrorObject> getContributorNetworkErrorResponse() {
        return mContributorRepository.getNetworkError();
    }

    public void deleteIssueRecordById(Integer id) {
        mIssueRepository.deleteIssueRecordById(id);
    }

    public LiveData<List<IssueDataModel>> loadIssues(String user, String repo, Boolean forceremote) {
        this.fromremote=forceremote;
        if ( (user!=null)&&(repo!=null))
        {searchstring=user+"/"+repo;}

        mApiIssueResponse.addSource(
                mIssueRepository.getIssues(user, repo, forceremote),
                apiIssueResponse -> mApiIssueResponse.setValue(apiIssueResponse)
        );
        return mApiIssueResponse;
    }


    @NonNull
    public LiveData<List<ContributorDataModel>> getApiContributorResponse() {
        return mApiContributorResponse;
    }


    public LiveData<List<ContributorDataModel>> loadContributor(@NonNull String user, String repo, Boolean forceremote) {

        mApiIssueResponse.addSource(
                mContributorRepository.getContributors(user, repo, forceremote),
                apiContributorResponse -> mApiContributorResponse.setValue(apiContributorResponse)
        );
        return mApiContributorResponse;
    }


    public void saveSearchString() {
        if ((searchstring!=null)&&(fromremote))
        {mPersistentStorageProxy.setSearchString(searchstring);}
    }

    public LiveData<String> getSearchString() {
        livedatasavedstring.setValue(mPersistentStorageProxy.getSearchString());
        return livedatasavedstring;
    }


    public MutableLiveData<String> getSnackBar() {
        return livedatasnackbar;
    }

    public void setSnackBar(String msg) {
        livedatasnackbar.setValue(msg);
    }

    public MutableLiveData<Boolean> getShowDialogIssueAndContributor() {
        return liveDataShowDialogIssueAndContributor;
    }

    public void setShowDialogIssueAndContributor(Boolean visible) {
        liveDataShowDialogIssueAndContributor.setValue(visible);
    }



    public void askNetWork() {
        liveDataIsInternetConnected.setValue(mCheckNetwork.isConnectedToInternet());
    }

    public MutableLiveData<Boolean> isInternetConnected() {
        return liveDataIsInternetConnected;
    }


}