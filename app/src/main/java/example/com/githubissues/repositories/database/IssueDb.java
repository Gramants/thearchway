package example.com.githubissues.repositories.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import example.com.githubissues.entities.IssueDataModel;

@Database(entities = IssueDataModel.class, version = 1)
public abstract class IssueDb extends RoomDatabase {
  public abstract IssueDao issueDao();
}
