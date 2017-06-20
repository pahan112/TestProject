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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
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

    private List<DataXYZ> dataXYZs = new ArrayList<>();
    private DataXYZ dataXYZ;

    @BindView(R.id.graph)
    GraphView graph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.schedule_fragment, null);
        ButterKnife.bind(this, view);
        dataXYZ = new DataXYZ();

        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setScrollable(true);
        viewport.setMinY(-10);
        viewport.setMaxY(10);

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
                if (!dataXYZs.isEmpty()) {
                    for (int i = 0; i < dataXYZs.size(); i++) {
                        Integer time = Integer.valueOf(dataXYZs.get(i).getTime()) % 1000;
                        Double x = Double.valueOf(dataXYZs.get(i).getX());
                        Double y = Double.valueOf(dataXYZs.get(i).getY());
                        Double z = Double.valueOf(dataXYZs.get(i).getZ());

                        LineGraphSeries<DataPoint> seriesX = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(time, x),

                        });
                        LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(time, z),

                        });
                        LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(time, y),

                        });

                        seriesX.setColor(Color.YELLOW);
                        seriesY.setColor(Color.RED);
                        seriesZ.setColor(Color.BLACK);

                        seriesX.setDrawBackground(true);
                        seriesZ.setThickness(10);

                        seriesX.setDrawDataPoints(true);
                        seriesY.setDrawDataPoints(true);
                        seriesZ.setDrawDataPoints(true);

                        graph.addSeries(seriesX);
                        graph.addSeries(seriesY);
                        graph.addSeries(seriesZ);
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
