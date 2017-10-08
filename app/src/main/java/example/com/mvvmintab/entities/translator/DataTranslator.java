package example.com.mvvmintab.entities.translator;

import java.util.ArrayList;
import java.util.List;

import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.entities.pojos.Contributor;
import example.com.mvvmintab.entities.pojos.Issue;
import retrofit2.Response;

/**
 * Created by Stefano on 29/09/2017.
 */

public class DataTranslator {

    public static ArrayList<IssueDataModel> IssueTranslator(Response<List<Issue>> response) {
        ArrayList<IssueDataModel> transformed = new ArrayList();
        if (response.isSuccessful()) {
            for (Issue issue : response.body()) {
                transformed.add(new IssueDataModel(issue.getId(), issue.getUrl(), issue.getRepositoryUrl(), issue.getNumber(), issue.getTitle(), issue.getState(), issue.getCreatedAt(), issue.getBody(), issue.getUser().getLogin(), issue.getUser().getUrl(), ""));
            }
        } else {
            transformed.add(new IssueDataModel(-1, "", "", 0, "", "", "", "", "", "", response.message()));
        }

        return transformed;
    }

    public static ArrayList<ContributorDataModel> ContributorTranslator(Response<List<Contributor>> contributors) {
        ArrayList<ContributorDataModel> transformed = new ArrayList();
        for (Contributor contributor : contributors.body()) {
            transformed.add(new ContributorDataModel(contributor.getId(), contributor.getLogin(), contributor.getHtml_url()));
        }
        return transformed;
    }


}
