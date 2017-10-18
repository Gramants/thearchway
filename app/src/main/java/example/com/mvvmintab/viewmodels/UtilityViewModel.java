package example.com.mvvmintab.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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


public class UtilityViewModel extends AndroidViewModel {

    final MutableLiveData<Boolean> liveDataShowDialogIssueAndContributor = new MutableLiveData<>();
    final MutableLiveData<String> livedatasavedstring = new MutableLiveData<>();
    final MutableLiveData<String> livedatasnackbar = new MutableLiveData<>();
    final MutableLiveData<Boolean> liveDataIsInternetConnected = new MutableLiveData<>();


    @Inject
    PersistentStorageProxy mPersistentStorageProxy;

    @Inject
    CheckNetwork mCheckNetwork;


    public UtilityViewModel(Application application) {
        super(application);
        ((App) application).getAppRepositoryComponent().inject(this);
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