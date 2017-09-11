package com.example.vmm408.taxiuserproject.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.vmm408.taxiuserproject.R;

public class MapActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

    }


//    private ValueEventListener getUserFromBase = new DatabaseValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            EventBus.getDefault().post(new EventSignInApi(dataSnapshot.getValue(UserModel.class)));
//        }
//    };
//
//    @Override
//    public void findUserInDataBase(String userId) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference(USERS_REF_KEY).child(userId);
//        reference.addListenerForSingleValueEvent(getUserFromBase);
//    }
}
