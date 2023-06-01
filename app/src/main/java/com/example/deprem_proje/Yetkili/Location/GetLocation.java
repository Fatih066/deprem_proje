package com.example.deprem_proje.Yetkili.Location;

import android.location.Location;

import com.example.deprem_proje.Firabase.FireStore;
import com.example.deprem_proje.Yetkili.Yetkili;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GetLocation {
    private FireStore fireStore = new FireStore();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public void getUids(DataUidCallback callback) {
        List<String> userUids = new ArrayList<>();
        fireStore.taskUid().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> userSnapshot = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot document : userSnapshot) {
                String documentId = document.getId();
                userUids.add(documentId);
            }
            callback.onDataReceived(userUids); // Veri alındığında geri çağırma mekanizması ile devam edilir
        });
    }
    public void getLocation(DataLocationCallback callback){
        List<Location> locations = new ArrayList<>();
        getUids(uids -> {
            for (String uid:uids) {
                firebaseFirestore.collection("Users").document(uid).collection("Konumum")
                        .document("Konumum").get()
                        .addOnSuccessListener(documentSnapshot -> {
                            double lat = Double.parseDouble(documentSnapshot.getData().get("latitude").toString());
                            double lon = Double.parseDouble(documentSnapshot.getData().get("longitude").toString());
                            Location location = new Location("");
                            location.setLatitude(lat);
                            location.setLongitude(lon);
                            locations.add(location);
                            callback.onDataReceived(locations);
                        });
            }
        });
    }
    public interface DataLocationCallback {
        void onDataReceived(List<Location> locations);
    }
    public interface DataUidCallback {
        void onDataReceived(List<String> uids);
    }

}
