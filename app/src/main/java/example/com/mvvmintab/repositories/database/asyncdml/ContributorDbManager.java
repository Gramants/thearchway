package example.com.mvvmintab.repositories.database.asyncdml;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.repositories.database.ProjectDb;

/**
 * Created by Stefano on 26/09/2017.
 */

public class ContributorDbManager {

    public static class AddContributorsAsyncTask extends AsyncTask<List<ContributorDataModel>, Void, Void> {

        private ProjectDb db;

        public AddContributorsAsyncTask(ProjectDb userDatabase) {
            db = userDatabase;
        }

        @Override
        protected Void doInBackground(List<ContributorDataModel>... contributors) {
            db.contributorDao().deleteAll();
            db.contributorDao().insert(contributors[0]);
            return null;

        }
    }

    public static class DeleteContributorByIdAsyncTask  extends AsyncTask<Integer, Void, Void> {
        private ProjectDb db;
        public DeleteContributorByIdAsyncTask(ProjectDb userDatabase) {db = userDatabase;}

        @Override
        protected Void doInBackground(Integer... integers) {
            db.contributorDao().deleteById(integers[0]);
            return null;
        }
    }
}
