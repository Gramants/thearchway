package example.com.mvvmarchcomp.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import example.com.mvvmarchcomp.entities.IssueDataModel;


public interface IssueRepository {

    LiveData<List<IssueDataModel>> getIssues(String owner, String repo, Boolean forceremote);

    LiveData<IssueDataModel> getIssueFromDb(int id);

    void deleteRecordById(int id);
}
