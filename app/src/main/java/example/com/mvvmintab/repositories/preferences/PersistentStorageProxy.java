package example.com.mvvmintab.repositories.preferences;

/**
 * Created by Stefano on 29/09/2017.
 */

public interface PersistentStorageProxy {
    String getSearchString();

    void setSearchString(String searchString);

    String getSearchStringTemp();

    void setSearchStringTemp(String searchString);
}
