package example.com.githubissues.entities.translator;

import java.util.ArrayList;
import java.util.List;

import example.com.githubissues.entities.IssueDataModel;
import example.com.githubissues.entities.pojos.Issue;
import retrofit2.Response;

/**
 * Created by Stefano on 29/09/2017.
 */

public class DataTranslator {

    public static ArrayList<IssueDataModel> IssueTranslator(Response<List<Issue>> issues) {
        ArrayList<IssueDataModel> transformed=new ArrayList();

        for (Issue issue : issues.body()) {
            transformed.add(new IssueDataModel(issue.getId(),issue.getUrl(),issue.getRepositoryUrl(),issue.getNumber(),issue.getTitle(),issue.getState(),issue.getCreatedAt(),issue.getBody(),issue.getUser().getLogin(),issue.getUser().getUrl()));
        }

        return transformed;
    }
}
