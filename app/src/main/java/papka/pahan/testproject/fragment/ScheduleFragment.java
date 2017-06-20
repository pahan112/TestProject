package papka.pahan.testproject.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import papka.pahan.testproject.R;
import papka.pahan.testproject.data.DataXYZ;

/**
 * Created by admin on 15.06.2017.
 */

public class ScheduleFragment extends Fragment {

    private DatabaseReference mDatabase;

    private List<DataXYZ> mDataXYZs = new ArrayList<>();

    @BindView(R.id.graph)
    GraphView graph;

    private LineGraphSeries<DataPoint> seriesX = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment, null);
        ButterKnife.bind(this, view);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                seriesX.resetData(new DataPoint[]{});
                seriesY.resetData(new DataPoint[]{});
                seriesZ.resetData(new DataPoint[]{});

                mDataXYZs.clear();
                mDatabase.child(dataSnapshot.getKey()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        DataXYZ dataXYZ = dataSnapshot.getValue(DataXYZ.class);
                        dataXYZ.setTime(dataSnapshot.getKey());
                        mDataXYZs.add(dataXYZ);

                        int time = Integer.valueOf(dataXYZ.getTime());
                        Double x = Double.valueOf(dataXYZ.getX());
                        Double y = Double.valueOf(dataXYZ.getY());
                        Double z = Double.valueOf(dataXYZ.getZ());

                        seriesX.appendData(new DataPoint(time, x), true, 10);
                        seriesY.appendData(new DataPoint(time, y), true, 10);
                        seriesZ.appendData(new DataPoint(time, z), true, 10);

                        graph.getViewport().setMaxX(time);
                        graph.getViewport().setMinX(Integer.valueOf(mDataXYZs.get(0).getTime()));

                        seriesX.setColor(Color.YELLOW);
                        seriesY.setColor(Color.RED);
                        seriesZ.setColor(Color.BLACK);

                        graph.addSeries(seriesX);
                        graph.addSeries(seriesY);
                        graph.addSeries(seriesZ);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}
