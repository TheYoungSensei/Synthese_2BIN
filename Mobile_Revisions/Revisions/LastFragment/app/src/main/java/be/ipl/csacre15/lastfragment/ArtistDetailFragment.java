package be.ipl.csacre15.lastfragment;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import be.ipl.csacre15.lastfragment.model.ArtistContent;

public class ArtistDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private ArtistContent.ArtistItem mItem;

    public ArtistDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = ArtistContent.items_map.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_detail, container, false);
        if (mItem != null) {
            ((WebView) rootView.findViewById(R.id.artist_detail)).loadUrl(mItem.details);
        }

        return rootView;
    }
}
