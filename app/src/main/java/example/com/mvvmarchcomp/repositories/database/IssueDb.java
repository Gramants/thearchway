package example.com.mvvmarchcomp.repositories.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import example.com.mvvmarchcomp.entities.IssueDataModel;

@Database(entities = IssueDataModel.class, version = 1)
public abstract class IssueDb extends RoomDatabase {
  public abstract IssueDao issueDao();
}
