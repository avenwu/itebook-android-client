package it_ebook.avenwu.com.itebooks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchHome extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText mEditView;
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
        mEditView = (EditText) view.findViewById(R.id.edit_query_content);
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
                    mListener.onFragmentInteraction(SearchHome.this, mEditView.getText().toString().trim());
//                    updateResult(mEditView.getText().toString().trim());
                }
            }
        });
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

    private void updateResult(String data) {
        Fragment list = new BooksFragmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        list.setArguments(bundle);
        Toast.makeText(getActivity(), "query received for:" + data, Toast.LENGTH_SHORT).show();
//        getChildFragmentManager().beginTransaction().replace(R.id.child_container, list).commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
