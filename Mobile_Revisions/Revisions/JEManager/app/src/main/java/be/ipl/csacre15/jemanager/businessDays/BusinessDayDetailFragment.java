package be.ipl.csacre15.jemanager.businessDays;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import be.ipl.csacre15.jemanager.Builder;
import be.ipl.csacre15.jemanager.R;

/**
 * A fragment representing a single BusinessDay detail screen.
 * This fragment is either contained in a {@link BusinessDayListActivity}
 * in two-pane mode (on tablets) or a {@link BusinessDayDetailActivity}
 * on handsets.
 */
public class BusinessDayDetailFragment extends Fragment implements BusinessModel.CompaniesObserver {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private BusinessDayContent.BusinessDayItem mItem;
    private BusinessModel model;
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = BusinessDayContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }

        model = ((Builder) getContext().getApplicationContext()).getModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.businessday_detail, container, false);
        model.registerCompaniesObserver(this);
        ConnectivityManager connMgr = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            model.newCompaniesTask(mItem).execute();
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.unregisterCompaniesObserver(this);
    }

    @Override
    public void notifyChange() {
        if(this.getContext() != null) {
            ArrayAdapter<String> ad = new ArrayAdapter<String>(
                    this.getContext().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, model.getCompanies());
            ListView v = (ListView) rootView.findViewById(R.id.businessday_detail);
            v.setAdapter(ad);
        }
    }
}
