package com.example.christopher.pae_master_flow;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.christopher.pae_master_flow.dummy.DetailsContent;

import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a single BusinessDay detail screen.
 * This fragment is either contained in a {@link BusinessDayListActivity}
 * in two-pane mode (on tablets) or a {@link BusinessDayDetailActivity}
 * on handsets.
 */
public class BusinessDayDetailFragment extends Fragment implements BusinessModel.Observer {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DetailsContent.DetailsItem mItem;
    private BusinessModel model;
    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BusinessDayDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DetailsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
        this.model = ((Builder) this.getContext().getApplicationContext()).getModel();
        model.registerCompaniesObserver(this);
        ConnectivityManager connMgr = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        ShowMyCompaniesTask task = new ShowMyCompaniesTask();
        if(networkInfo != null && networkInfo.isConnected()) {
            task.execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.businessday_detail, container, false);

        // Show the dummy content as text in a TextView.
        /*if (mItem != null) {

            ((TextView) rootView.findViewById(R.id.businessday_detail)).setText(mItem.details);
        }*/
        onCreate(savedInstanceState);
        return rootView;
    }

    @Override
    public void notifyChange() {
        if(this.getContext() == null)
            return;
        ArrayAdapter<String> ad = new ArrayAdapter<String>(
                this.getContext().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, model.displayCompanies());
        ListView v = (ListView) rootView.findViewById(R.id.businessday_detail);
        v.setAdapter(ad);
    }

    public class ShowMyCompaniesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String s = null;
            try {
                Map<String, String> map = new HashMap<String, String>();
                map.put("json", mItem.details);
                map.put("type", "showConfirmedEventParticipations");
                s = HTTPUtils.performPostCall(HTTPUtils.URL, map);
            } catch (HTTPUtils.HTTPException exception) {

            } catch (HTTPUtils.HTTPNetworkException exception) {

            }
            return s;
        }


        @Override
        protected void onPostExecute(String s) {
            model.showCompanies(s);
        }
    }
}
