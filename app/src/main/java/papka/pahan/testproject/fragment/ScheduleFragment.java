package papka.pahan.testproject.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

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

    final String LOG_TAG = "myLogs";

    private List<DataXYZ> dataXYZs = new ArrayList<>();
    private DataXYZ dataXYZ;

    @BindView(R.id.graph)
    GraphViewXML graph;

//    private LineGraphSeries<DataPoint> seriesX;
//    private LineGraphSeries<DataPoint> seriesY;
//    private LineGraphSeries<DataPoint> seriesZ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.schedule_fragment,null);
        ButterKnife.bind(this,view);
        dataXYZ = new DataXYZ();





        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sessionSnap : dataSnapshot.getChildren()) {
                    dataXYZs.clear();
                    for (DataSnapshot postSnapshot : sessionSnap.getChildren()) {
                        DataXYZ dataXYZ = postSnapshot.getValue(DataXYZ.class);
                        dataXYZ.setTime(postSnapshot.getKey());
                        dataXYZs.add(dataXYZ);
                    }
                }
                graph.removeAllSeries();
                if(!dataXYZs.isEmpty()){
                for (int i = 0; i < dataXYZs.size(); i++) {
                    Integer time = Integer.valueOf(dataXYZs.get(i).getTime());
                    Double x = Double.valueOf(dataXYZs.get(i).getX());
                    Double y = Double.valueOf(dataXYZs.get(i).getY());
                    Double z = Double.valueOf(dataXYZs.get(i).getZ());

                    PointsGraphSeries<DataPoint> seriesX = new PointsGraphSeries<>(new DataPoint[] {
                            new DataPoint(time, x),

                    });
                    PointsGraphSeries<DataPoint> seriesZ = new PointsGraphSeries<>(new DataPoint[] {
                            new DataPoint(time, z),

                    });
                    PointsGraphSeries<DataPoint> seriesY = new PointsGraphSeries<>(new DataPoint[] {
                            new DataPoint(time, y),

                    });
//                    seriesX.appendData(new DataPoint(time,x),false,900000000);
//                    seriesY.appendData(new DataPoint(time,y),false,900000000);
//                    seriesZ.appendData(new DataPoint(time,z),false,900000000);

//                    Log.d(LOG_TAG,time + "");
//                    Log.d(LOG_TAG,x+ " ");
//                    Log.d(LOG_TAG,dataXYZs.get(i).getZ());
//
//                   seriesX = new LineGraphSeries<>(new DataPoint[] {
//                            new DataPoint(time, x),
//                    });
//                    seriesX.setColor(Color.RED);
//
//                    seriesY = new LineGraphSeries<>(new DataPoint[] {
//                            new DataPoint(time, y),
//                    });
//                    seriesX.setColor(Color.GREEN);
//                    seriesZ = new LineGraphSeries<>(new DataPoint[] {
//                            new DataPoint(time, z),
//                    });
                    graph.getViewport().setScalable(true);

// activate horizontal scrolling
                    graph.getViewport().setScrollable(true);


                    seriesX.setColor(Color.YELLOW);
                    seriesY.setColor(Color.RED);
                    seriesZ.setColor(Color.BLACK);
                    graph.addSeries(seriesX );
                    graph.addSeries(seriesY );
                    graph.addSeries(seriesZ );
                }}




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
}
