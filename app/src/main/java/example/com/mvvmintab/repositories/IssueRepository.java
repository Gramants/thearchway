package example.com.mvvmintab.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.NetworkErrorObject;


public interface IssueRepository {

    LiveData<List<IssueDataModel>> getIssues(String owner, String repo, Boolean forceremote);

    LiveData<NetworkErrorObject> getNetworkError();

    LiveData<IssueDataModel> getIssueFromDb(int id);

    void deleteIssueRecordById(int id);


}
