package example.com.mvvmintab;

import java.util.Comparator;

import example.com.mvvmintab.entities.ContributorDataModel;
import example.com.mvvmintab.entities.IssueDataModel;

/**
 * Created by Stefano on 18/10/2017.
 */

public class Utils {

    public static class CustomComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {

            if ((o1 instanceof ContributorDataModel) && (o2 instanceof ContributorDataModel)) {
                return ((ContributorDataModel) o1).getLogin().compareTo(((ContributorDataModel) o2).getLogin());
            } else if ((o1 instanceof IssueDataModel) && (o2 instanceof IssueDataModel)) {
                return ((IssueDataModel) o1).getTitle().compareTo(((IssueDataModel) o2).getTitle());
            }
            return 0;
        }
    }
}
