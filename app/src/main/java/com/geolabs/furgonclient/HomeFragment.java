package com.geolabs.furgonclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by delor34n on 20-07-15.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    View rootView;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_home, container,
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
            mainActivity.startActivity(startMain);
            //Update the status
            mainActivity.setServiceStatus(true);
        } else {
            //Stops the service
            mainActivity.stopService(new Intent(mainActivity, BackgroundService.class));
            //Updates the status
            mainActivity.setServiceStatus(false);
            //Updates the status text in the view.
            TextView vServiceStatus = (TextView)this.rootView.findViewById(R.id.serviceStatus);
            vServiceStatus.setText(getString(R.string.service_stoped));
            //Display message
            Toast.makeText(getActivity(), getString(R.string.service_stoped), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }
}