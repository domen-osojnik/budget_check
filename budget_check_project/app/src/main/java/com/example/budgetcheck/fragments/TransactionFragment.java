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
import android.widget.CheckBox;

import com.example.budgetcheck.MainActivity;
import com.example.budgetcheck.MyApplicationClass;
import com.example.budgetcheck.R;
import com.example.budgetcheck.events.InfoEvent;
import com.example.datastructurelib.Uporabnik;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {

    private static final String CHANNEL_ID = "CID";

    private static final String TAG ="TRANSACTION_INFO";

    TextInputEditText locationName;
    TextInputEditText amount;
    Button addButton;
    CheckBox spent;
    private FusedLocationProviderClient fusedLocationClient;
    private String uID= "";
    private String accID = "";
    private Double accBalance = 0.00;
    private MyApplicationClass myApplicationClass;

    MainActivity activity;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        this.uID = activity.getMyId();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final String TAG = "GET_ACC";

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        databaseRef.child("users").child(this.uID).child("accounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot==null)return;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    accID = postSnapshot.getKey();
                    accBalance = new Double (postSnapshot.child("stanje").getValue().toString());
                    Log.d("Pridobljeno stanje: ", accBalance.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.locationName = (TextInputEditText)getView().findViewById(R.id.location_text);
        this.amount = (TextInputEditText)getView().findViewById(R.id.amount_text);
        this.addButton = (Button)getView().findViewById(R.id.add_button);
        this.spent = (CheckBox) getView().findViewById(R.id.earned);

        this.myApplicationClass = (MyApplicationClass) getActivity().getApplication();

        Uporabnik user = myApplicationClass.getUporabnik();

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
                Log.d("ACC_ID", accID);
                myApplicationClass.HandleTranscationCreation(locationName.getText().toString(), new Double(amount.getText().toString()), new Boolean(spent.isChecked()), uID, accID, accBalance);
                showNotification();
            }
        });
    }

    void showNotification(){
        // Create an explicit intent for an Activity in your app
        // Za odpiranje domače strani po pritisku na notification
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("UID", this.uID);
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
