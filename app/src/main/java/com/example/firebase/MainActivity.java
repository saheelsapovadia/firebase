package com.example.firebase;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView name_t, membership_t, age_t, dob_t;
    Button button;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String name, age,membership;
    Long  dob;
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
                db_p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    List<DocumentSnapshot> db_list;
                                    QuerySnapshot document = task.getResult();
                                    db_list = document.getDocuments();

                                    name = (String) db_list.get(0).get("name");
                                    membership = (String) db_list.get(0).get("membership");
                                    age = (String) db_list.get(0).get("age");
                                    dob = (Long) db_list.get(0).get("DOB");

                                    name_t.setText(name);
                                    age_t.setText(""+age);
                                    membership_t.setText(membership);
                                    dob_t.setText(""+dob);


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });



            }
        });

    }


}