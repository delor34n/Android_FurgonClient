package com.geolabs.furgonclient.furgonclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

/**
 * Created by delor34n on 20-07-15.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);

        ToggleButton startserviceButton = (ToggleButton) rootView.findViewById(R.id.btnStartRoute);
        startserviceButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = ((MainActivity) this.getActivity());
        //If the serviceStatus is false
        if(!mainActivity.getServiceStatus()) {
            //Starts the service
            mainActivity.startService(new Intent(getActivity(), BackgroundService.class));
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            //Update the status
            mainActivity.setServiceStatus(true);
        } else {
            //Stops the service
            mainActivity.stopService(new Intent(mainActivity, BackgroundService.class));
            //Updates the status
            mainActivity.setServiceStatus(false);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }
}
