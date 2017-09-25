package example.com.githubissues.repositories.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import example.com.githubissues.entities.Issue;
import example.com.githubissues.entities.IssueLinearized;


@Dao
public interface IssueDao {
  @Query("SELECT * FROM Issues")
  LiveData<List<IssueLinearized>> getAllIssue();

  @Query("SELECT * FROM Issues where id = :id")
  LiveData<IssueLinearized> getIssueById(int id);

  @Query("DELETE FROM Issues")
  void deleteAll();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(IssueLinearized issue);

}
