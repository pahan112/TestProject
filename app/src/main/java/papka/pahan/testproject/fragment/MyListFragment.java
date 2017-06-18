package papka.pahan.testproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import papka.pahan.testproject.R;
import papka.pahan.testproject.data.DataXYZ;

/**
 * Created by admin on 15.06.2017.
 */

public class MyListFragment extends Fragment {

    private DatabaseReference mDatabase;
    Set<String> sesia = new HashSet<>();
    final String LOG_TAG = "myLogs";
    @BindView(R.id.text)
    TextView text;

    String string;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.list_fragment, null);
        ButterKnife.bind(this, view);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sessionSnap : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : sessionSnap.getChildren()) {
                        DataXYZ dataXYZ = postSnapshot.getValue(DataXYZ.class);
                        dataXYZ.setTime(postSnapshot.getKey());
                        Log.e(LOG_TAG, dataXYZ.toString());

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;

    }

}
