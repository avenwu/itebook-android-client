package it_ebook.avenwu.com.itebooks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchHome()).commit();
    }

    @Override
    public void onFragmentInteraction(Fragment fragment, String data) {
        if (fragment instanceof SearchHome) {
            Fragment list = new BooksFragmentFragment();
            Bundle bundle = new Bundle();
            bundle.putString("data", data);
            list.setArguments(bundle);
            Toast.makeText(this, "query received for:" + data, Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, list).addToBackStack(null).commit();
        }
    }
}
