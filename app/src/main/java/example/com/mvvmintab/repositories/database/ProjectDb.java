package example.com.mvvmintab.repositories.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;

@Database(entities = {IssueDataModel.class,ContributorDataModel.class}, version = 1)
public abstract class ProjectDb extends RoomDatabase {
  public abstract IssueDao issueDao();
  public abstract ContributorDao contributorDao();
}
