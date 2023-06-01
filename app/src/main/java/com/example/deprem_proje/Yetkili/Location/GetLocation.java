package com.example.deprem_proje.Yetkili.Location;

import com.example.deprem_proje.Firabase.FireStore;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    public void setMarkers(DataLocationCallback callback){
        List<MarkerOptions> markerOptions = new ArrayList<>();
        getUids(uids -> {
            int uidCount = uids.size();
            AtomicInteger counter = new AtomicInteger(0);
            for (String uid:uids) {
                firebaseFirestore.collection("Users").document(uid).collection("Konumum")
                        .document("Konumum").get()
                        .addOnSuccessListener(documentSnapshot -> {
                            boolean isSafe = Boolean.parseBoolean(documentSnapshot.getData().get("isSafe").toString());
                            if (!isSafe){
                                double lat = Double.parseDouble(documentSnapshot.getData().get("latitude").toString());
                                double lon = Double.parseDouble(documentSnapshot.getData().get("longitude").toString());
                                MarkerOptions markerOption = new MarkerOptions();
                                markerOption.position(new LatLng(lat, lon)).snippet(uid);
                                firebaseFirestore.collection("Users").document(uid).get().addOnSuccessListener(snapshot -> {
                                  String  name = snapshot.getData().get("name").toString();
                                    markerOption.title(name);
                                    markerOptions.add(markerOption);
                                    markerOptions.add(markerOption);
                                    int count = counter.incrementAndGet();
                                    if (count == uidCount) {
                                        callback.onDataReceived(markerOptions);
                                    }
                                });
                            }
                            else{
                                int count = counter.incrementAndGet();
                                if (count == uidCount)
                                    callback.onDataReceived(markerOptions);
                            }
                        });
            }

        });
    }
    public interface DataLocationCallback {
        void onDataReceived(List<MarkerOptions> markerOptions);
    }
    public interface DataUidCallback {
        void onDataReceived(List<String> uids);
    }

}
