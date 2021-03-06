package it_ebook.avenwu.com.itebooks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class BooksFragmentFragment extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks<BooksResult> {

    private OnFragmentInteractionListener mListener;
    private int mPages = 1;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private BaseAdapter mAdapter;
    private BooksResult mData;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BooksFragmentFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_booksfragment, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mProgressBar = new ProgressBar(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mProgressBar.setLayoutParams(params);
        view.addView(mProgressBar);
        return view;
    }

    @SuppressWarnings("NewApi")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mData == null ? 0 : mData.getBooks().size();
            }

            @Override
            public Book getItem(int position) {
                return mData == null || mData.getBooks() == null || mData.getBooks().size() - 1 < position ? null : mData.getBooks().get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
                    holder = new ViewHolder();
                    convertView = View.inflate(getActivity(), R.layout.book_item, null);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    holder.description = (TextView) convertView.findViewById(R.id.description);
                    holder.image = (ImageView) convertView.findViewById(R.id.image);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                Book book = getItem(position);
                if (book != null) {
                    holder.title.setText(book.getTitle());
                    holder.description.setText(book.getDescription());
                    Picasso.with(getActivity()).load(book.getImage()).into(holder.image);
                }
                return convertView;
            }
        };
        mListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mAdapter.getItem(position)) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("book", (Book) mAdapter.getItem(position));
            startActivity(intent);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public Loader<BooksResult> onCreateLoader(int id, Bundle args) {
        return new BaseLoader<BooksResult>(getActivity()) {

            @Override
            public BooksResult loadInBackground() {
                if (getArguments() == null || TextUtils.isEmpty(getArguments().getString("data")))
                    return null;
                return ApiService.getService().queryBooksonPage(getArguments().getString("data"), mPages);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<BooksResult> loader, BooksResult data) {
        mProgressBar.setVisibility(View.GONE);
        if (data != null && data.getBooks() != null) {
            if (data.getPage() == 1) {
                mData = data;
            } else {
                mData.getBooks().addAll(data.getBooks());
            }
            mAdapter.notifyDataSetChanged();
            for (Book book : data.getBooks()) {
                Log.d("onLoadFinished", book.getTitle());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<BooksResult> loader) {

    }

    static class ViewHolder {
        TextView title;
        TextView description;
        ImageView image;
    }
}
