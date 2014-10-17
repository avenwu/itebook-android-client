package it_ebook.avenwu.com.itebooks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class SearchHome extends Fragment implements LoaderManager.LoaderCallbacks<String[]> {

    private OnFragmentInteractionListener mListener;
    private AutoCompleteTextView mEditView;
    private TextView mWarnText;

    public SearchHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWarnText = (TextView) view.findViewById(R.id.tv_warn);
        mEditView = (AutoCompleteTextView) view.findViewById(R.id.edit_query_content);
        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int length = mEditView.getText().toString().trim().length();
                if (length <= 0) {
                    mWarnText.setText(R.string.warn_empty_content);
                    mWarnText.setVisibility(View.VISIBLE);
                } else if (length > 50) {
                    mWarnText.setText(R.string.warn_length_reach_limit);
                    mWarnText.setVisibility(View.GONE);
                } else {
                    mWarnText.setText("");
                    mWarnText.setVisibility(View.GONE);
                    final String query = mEditView.getText().toString().trim();
                    Utils.addSearchItem(getActivity(), query);
                    mListener.onFragmentInteraction(SearchHome.this, query);
                }
            }
        });
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        return new BaseLoader<String[]>(getActivity()) {

            @Override
            public String[] loadInBackground() {
                String[] locale = getResources().getStringArray(R.array.auto_complete_items);
                final String[] searchItems = Utils.getSearchItems(getActivity());
                if (searchItems != null && searchItems.length > 0) {
                    String[] temp = new String[locale.length + searchItems.length];
                    int index = 0;
                    for (String item : locale) {
                        temp[index] = item;
                        index++;
                    }
                    for (String item : searchItems) {
                        temp[index] = item;
                        index++;
                    }
                    return temp;
                } else {
                    return locale;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        if (data != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
            mEditView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
