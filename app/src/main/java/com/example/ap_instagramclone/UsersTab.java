package com.example.ap_instagramclone;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment {

    private ArrayList<String> arrayList;

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    // below is an empty constructor that is autogenerated in the fragment
    public UsersTab() {
        // Required empty public constructor
    }

    // this interface is also autogenerated as is, the onCreateView, up to the end of the inflater statement
    public interface OnFragmentInteractionListener{
        // TODO:  Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        // assign the ListView
        mListView = view.findViewById(R.id.listView);

        // assign the array for storing the usernames in the for loop below
        arrayList = new ArrayList<>();

        // set the type of parse query to <ParseUser>
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

        // flter out the currentUser using "where not equal to" followed by what we want to filter out
        // that exception is the user name and we get it using ParseUser.get.....
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        // now we create the "findInBackground method with a callback to find all objects that match the query of type ParseUser
        // less the excluded current user
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if(e==null){
                    if(users.size()>0){
                        for(ParseUser user: users){
                            // this is where we will create our array list, but we first needed to initialize and define
                            // that array in our class userTab and in the onCreateView, above

                            // the parameters of ArrayAdapter are; the context, the line item
                            // designated to populate the array, and the name of our array
                            mAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_list_item_1, arrayList);

                            // add a username from the parse server to the list, iteratively according to for stmt
                            arrayList.add(user.getUsername());

                            // let the adapter know where we are in the list as iteration progresses
                            mAdapter.notifyDataSetChanged();

                        }
                        // set the adapter to our ListView, mListView so we can see the list on the screen(ListView)
                        mListView.setAdapter(mAdapter);
                    }
                }
            }
        });
        return view;

    }
}
