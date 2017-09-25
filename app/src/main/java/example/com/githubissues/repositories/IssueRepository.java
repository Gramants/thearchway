package example.com.githubissues.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import example.com.githubissues.entities.IssueLinearized;


public interface IssueRepository {

    LiveData<List<IssueLinearized>> getIssues(String owner, String repo, Boolean forceremote);

    LiveData<IssueLinearized> getIssueFromDb(int id);
}
