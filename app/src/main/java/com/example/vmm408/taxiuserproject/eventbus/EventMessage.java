package com.example.vmm408.taxiuserproject.eventbus;

import com.google.firebase.database.DataSnapshot;

public class EventMessage {
    private DataSnapshot dataSnapshot;

    public EventMessage(DataSnapshot dataSnapshot) {
        this.dataSnapshot = dataSnapshot;
    }

    public DataSnapshot getDataSnapshot() {
        return dataSnapshot;
    }
}
