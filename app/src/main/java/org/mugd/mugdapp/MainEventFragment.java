package org.mugd.mugdapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainEventFragment extends Fragment {

    private String eventId;
    private Events event;

    TextView textViewDesc;
    TextView textViewVenue;
    TextView textViewDate;

    public MainEventFragment(){
        this.event = EventShow.event;
        this.eventId = event.id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_main_event, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();


        textViewVenue = (TextView) getView().findViewById(R.id.eventVenue);
        textViewDesc = (TextView) getView().findViewById(R.id.eventDescription);
        textViewDate = (TextView) getView().findViewById(R.id.eventDate);

        textViewDate.setText(Events.extract(event,"Date"));
        textViewVenue.setText(Events.extract(event, "college"));
        String description = Events.extract(event, "Desc");
        textViewDesc.setText(description);
        textViewDesc.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
