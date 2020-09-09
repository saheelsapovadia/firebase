package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView name_t, membership_t, age_t, dob_t;
    Button button;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name, age, membership;
    Long dob;
    CollectionReference db_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //---------HOOKS-------
        button = findViewById(R.id.name_btn);
        name_t = findViewById(R.id.name_txt);
        age_t = findViewById(R.id.age_txt);
        dob_t = findViewById(R.id.dob_txt);
        membership_t = findViewById(R.id.membership_txt);


        db_p = db.collection("users").document("saheel").collection("saheel");
        button.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "MyActivity";

            @Override
            public void onClick(View view) {
                db_p.document("personal_info").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                   @Override
                   public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException e) {
                       if (e != null) {
                           Log.w(TAG, "Listen failed.", e);
                           return;
                       }

                       if (value != null && value.exists()) {
                           Log.d(TAG, "Current data: " + value.getData());
                           name = (String) value.get("name");
                           membership = (String) value.get("membership");
                           age = (String) value.get("age");
                           dob = (Long) value.get("DOB");


                           name_t.setText(name);
                           age_t.setText("" + age);
                           membership_t.setText(membership);
                           dob_t.setText("" + dob);
                       } else {
                           Log.d(TAG, "Current data: null");
                       }
                   }
               });

               //db_p.document("personal_info").get().getResult()
            }
        });

    }


}