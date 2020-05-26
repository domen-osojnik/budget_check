package com.example.budgetcheck.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.budgetcheck.MainActivity;
import com.example.budgetcheck.MyApplicationClass;
import com.example.budgetcheck.R;
import com.example.datastructurelib.Uporabnik;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {

    private static final String CHANNEL_ID = "CID";

    private static final String TAG ="TRANSACTION_INFO";

    TextInputEditText locationName;
    TextInputEditText amount;
    Button addButton;
    private FusedLocationProviderClient fusedLocationClient;
    private String uID="";
    private MyApplicationClass myApplicationClass;

    public TransactionFragment() {
        // Required empty public constructor
    }

    public TransactionFragment(String uID) {
        this.uID = uID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("Ustvarjen fragment", this.uID);
        this.locationName = (TextInputEditText)getView().findViewById(R.id.location_text);
        this.amount = (TextInputEditText)getView().findViewById(R.id.amount_text);
        this.addButton = (Button)getView().findViewById(R.id.add_button);

        this.myApplicationClass = new MyApplicationClass();

        Uporabnik user = myApplicationClass.getUporabnik();

        Log.d("test", user.toString());

        createNotificationChannel();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(TAG, String.valueOf(location.getLatitude()));
                            Log.d(TAG, String.valueOf(location.getLongitude()));

                            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                            try {
                                List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if(null!=listAddresses&&listAddresses.size()>0){
                                    String _Location = listAddresses.get(0).getAddressLine(0);
                                    Log.d(TAG, _Location);
                                    locationName.setText(_Location);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        this.addButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showNotification();
            }
        });
    }

    void showNotification(){
        // Create an explicit intent for an Activity in your app
        // Za odpiranje domače strani po pritisku na notification
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        String textTitle = "Uspešno ustvarjena transakcija!";
        String textContent = "Lahko si jo ogledaš na domači strani.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_envelope_regular)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)        // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent).setAutoCancel(true);

        int notificationId = 257185;
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
