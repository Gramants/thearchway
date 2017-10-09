package example.com.mvvmintab.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.mvvmintab.R;
import example.com.mvvmintab.entities.ContributorDataModel;

/**
 * Created by shahbaz on 14/05/17.
 */
public class ContributorDataAdapter extends RecyclerView.Adapter<ContributorDataAdapter.Holder> {

    private final LayoutInflater mInflator;
    private List<ContributorDataModel> mContributorList;

    public ContributorDataAdapter(LayoutInflater inflator) {
        mInflator = inflator;
        mContributorList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflator.inflate(R.layout.issue_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.mTextViewTitle.setText("contribute of");
        String id = String.valueOf(mContributorList.get(position).getId());
        holder.mTextViewId.setText(id);
        holder.mTextViewCreator.setText(mContributorList.get(position).getLogin());


    }

    @Override
    public int getItemCount() {
        return mContributorList.size();
    }

    public void addContributors(List<ContributorDataModel> issues) {
        //mContributorList.clear();
        mContributorList.addAll(issues);
        notifyDataSetChanged();
    }

    public void clearContributors() {
        mContributorList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewId;
        TextView mTextViewCreator;

        public Holder(View v) {
            super(v);
            mTextViewTitle = (TextView) v.findViewById(R.id.title);
            mTextViewId = (TextView) v.findViewById(R.id.issue_id);
            mTextViewCreator = (TextView) v.findViewById(R.id.creator_name);
        }
    }
}