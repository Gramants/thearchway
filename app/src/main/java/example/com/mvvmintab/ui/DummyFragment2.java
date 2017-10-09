package example.com.mvvmintab.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.List;

import example.com.mvvmintab.R;
import example.com.mvvmintab.entities.IssueDataModel;
import example.com.mvvmintab.viewmodels.RootViewModel;


@SuppressLint("ValidFragment")
public class DummyFragment2 extends LifecycleFragment {
    int color;
    private RootViewModel mRootViewModel;
    private List<IssueDataModel> caches;

    @SuppressLint("ValidFragment")
    public DummyFragment2(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.itemlist_fragment, container, false);
        final RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.dummyfrag_bg);
        relativeLayout.setBackgroundColor(color);

        mRootViewModel = ViewModelProviders.of(getActivity()).get(RootViewModel.class);

        return view;
    }




}

