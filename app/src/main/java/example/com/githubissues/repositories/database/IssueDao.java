package example.com.githubissues.repositories.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import example.com.githubissues.entities.Issue;


@Dao
public interface IssueDao {
  @Query("SELECT * FROM Issues")
  LiveData<List<Issue>> getAllIssue();

  @Query("SELECT * FROM Issues where id = :id")
  LiveData<Issue> getIssueById(int id);

  @Query("DELETE FROM Issues")
  void deleteAll();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Issue issue);

}
