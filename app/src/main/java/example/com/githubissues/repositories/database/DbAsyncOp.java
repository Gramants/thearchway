package example.com.githubissues.repositories.database;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import example.com.githubissues.entities.IssueDataModel;

/**
 * Created by Stefano on 26/09/2017.
 */

public class DbAsyncOp {

    public static class AddIssueAsyncTask extends AsyncTask<List<IssueDataModel>, Void, Void> {

        private IssueDb db;

        public AddIssueAsyncTask(IssueDb userDatabase) {
            db = userDatabase;
        }

        @Override
        protected Void doInBackground(List<IssueDataModel>... issues) {
            db.issueDao().deleteAll();
            List<IssueDataModel> results = new ArrayList<IssueDataModel>();
            results=issues[0];
            for (IssueDataModel issue : results) {
                db.issueDao().insert(issue);
            }
            return null;

        }
    }

    public static class DeleteIssueByIdAsyncTask  extends AsyncTask<Integer, Void, Void> {
        private IssueDb db;
        public DeleteIssueByIdAsyncTask(IssueDb userDatabase) {db = userDatabase;}

        @Override
        protected Void doInBackground(Integer... integers) {
            db.issueDao().deleteById(integers[0]);
            return null;
        }
    }
}
