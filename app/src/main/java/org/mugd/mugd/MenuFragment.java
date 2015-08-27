package org.mugd.mugd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;


public class MenuFragment extends Fragment {

    private static final String TAG = "MenuFragment";

    private Activity activity;

    private LinearLayout ll;
    private FragmentActivity fa;

    RecyclerView rv;
    List<MenuItems> mi;

    public MenuFragment() {
        this.mi = MainActivity.mi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragementView = inflater.inflate(R.layout.fragment_menu, container, false);

        rv = (RecyclerView) myFragementView.findViewById(R.id.rvMenu);

        if(rv == null){
            Log.e(TAG, "rv shouldnot be null");
        }

        rv.setHasFixedSize(true);

//        LinearLayoutManager llm = new LinearLayoutManager(activity.getApplicationContext());
//        rv.setLayoutManager(llm);

  //      rv.addItemDecoration(new MarginDecoration(activity));
        rv.setLayoutManager(new GridLayoutManager(activity, 2));
  //      rv.setAdapter(new NumberedAdapter(30));

        MenuAdapter adapter = new MenuAdapter(activity,mi);
        rv.setAdapter(adapter);

        return myFragementView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity==null){
            Log.e(TAG,"Why is Activity NULL !! ");
        }else {
            this.activity = activity;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
