package example.com.githubissues.repositories;

import android.arch.lifecycle.LiveData;

import java.util.List;

import example.com.githubissues.entities.Issue;


public interface IssueRepository {

    LiveData<List<Issue>> getIssues(String owner, String repo, Boolean forceremote);
}
