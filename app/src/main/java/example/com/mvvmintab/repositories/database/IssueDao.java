package example.com.mvvmintab.repositories.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import example.com.mvvmintab.entities.IssueDataModel;


@Dao
public interface IssueDao {
  @Query("SELECT * FROM Issues")
  LiveData<List<IssueDataModel>> getAllIssue();

  @Query("SELECT * FROM Issues where id = :id")
  LiveData<IssueDataModel> getIssueById(int id);

  @Query("DELETE FROM Issues")
  void deleteAll();

  @Query("DELETE FROM Issues where  id = :id")
  void deleteById(int id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(IssueDataModel issue);

}
