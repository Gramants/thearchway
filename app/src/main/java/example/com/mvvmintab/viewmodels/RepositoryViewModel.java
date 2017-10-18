package example.com.mvvmintab.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
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
import example.com.mvvmintab.entities.QueryString;
import example.com.mvvmintab.repositories.ContributorRepository;
import example.com.mvvmintab.repositories.IssueRepository;
import example.com.mvvmintab.repositories.api.checknetwork.CheckNetwork;
import example.com.mvvmintab.repositories.preferences.PersistentStorageProxy;


public class RepositoryViewModel extends AndroidViewModel {
    final MutableLiveData<QueryString> mQueryStringObject = new MutableLiveData<>();

    private MediatorLiveData<List<IssueDataModel>> mApiIssueResponse;
    private MediatorLiveData<List<ContributorDataModel>> mApiContributorResponse;
    private LiveData<List<IssueDataModel>> mResultIssueDataModel;
    private LiveData<List<ContributorDataModel>> mResultContributorDataModel;

    @Inject
    IssueRepository mIssueRepository;

    @Inject
    ContributorRepository mContributorRepository;

    @Inject
    PersistentStorageProxy mPersistentStorageProxy;


    public RepositoryViewModel(Application application) {
        super(application);
        mApiIssueResponse = new MediatorLiveData<>();
        mApiContributorResponse = new MediatorLiveData<>();
        ((App) application).getAppRepositoryComponent().inject(this);

// transformations of queryohject to result streaming of issues and contributors

        mResultIssueDataModel = Transformations.switchMap(mQueryStringObject,mQueryStringObject->{
            return loadIssues(mQueryStringObject.getUser(),mQueryStringObject.getRepo(),mQueryStringObject.getForceremote());
        });

        mResultContributorDataModel = Transformations.switchMap(mQueryStringObject,mQueryStringObject->{
            return loadContributor(mQueryStringObject.getUser(),mQueryStringObject.getRepo(),mQueryStringObject.getForceremote());
        });

    }



    public void setQueryString(String user, String repo, boolean forceremote) {
        mQueryStringObject.setValue(new QueryString(user,repo,forceremote));
    }



    public LiveData<List<IssueDataModel>> loadIssues(String user, String repo, Boolean forceremote) {
        mApiIssueResponse.addSource(
                mIssueRepository.getIssues(user, repo, forceremote),
                apiIssueResponse -> mApiIssueResponse.setValue(apiIssueResponse)
        );
        // save searchstring only if some data from remote
        if ((forceremote) && (getApiIssueResponse().getValue().size()>0)) {
            saveSearchString(user + "/" + repo);
        }
        return mApiIssueResponse;
    }


    public LiveData<List<ContributorDataModel>> loadContributor(@NonNull String user, String repo, Boolean forceremote) {
        mApiIssueResponse.addSource(
                mContributorRepository.getContributors(user, repo, forceremote),
                apiContributorResponse -> mApiContributorResponse.setValue(apiContributorResponse)
        );
        return mApiContributorResponse;
    }




    public void deleteIssueRecordById(Integer id) {
        mIssueRepository.deleteIssueRecordById(id);
    }


    @NonNull
    public LiveData<List<ContributorDataModel>> getApiContributorResponse() {
        return mResultContributorDataModel;
    }

    @NonNull
    public LiveData<List<IssueDataModel>> getApiIssueResponse() {
        return mResultIssueDataModel;
    }


    public LiveData<NetworkErrorObject> getIssueNetworkErrorResponse() {
        return mIssueRepository.getNetworkError();
    }

    public LiveData<NetworkErrorObject> getContributorNetworkErrorResponse() {
        return mContributorRepository.getNetworkError();
    }


    public void saveSearchString(String searchstring) {
        mPersistentStorageProxy.setSearchStringTemp(searchstring);
    }

}