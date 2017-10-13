package example.com.mvvmintab.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.NetworkErrorObject;


public interface ContributorRepository {

    LiveData<List<ContributorDataModel>> getContributors(String owner, String repo, Boolean forceremote);

    LiveData<ContributorDataModel> getContributorFromDb(int id);

    LiveData<NetworkErrorObject> getNetworkError();
}
