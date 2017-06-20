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

    private LineGraphSeries<DataPoint> seriesX = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesY = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesZ = new LineGraphSeries<>();

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
                seriesX.resetData(new DataPoint[] {});
                seriesY.resetData(new DataPoint[] {});
                seriesZ.resetData(new DataPoint[] {});
                for (DataSnapshot sessionSnap : dataSnapshot.getChildren()) {
                    dataXYZs.clear();
                    for (DataSnapshot postSnapshot : sessionSnap.getChildren()) {
                        DataXYZ dataXYZ = postSnapshot.getValue(DataXYZ.class);
                        dataXYZ.setTime(postSnapshot.getKey());
                        dataXYZs.add(dataXYZ);
                    }
                }
                if (!dataXYZs.isEmpty()) {
                    for (int i = 0; i < dataXYZs.size(); i++) {
                        Double time = Double.valueOf(dataXYZs.get(i).getTime()) % 1000;
                        Double x = Double.valueOf(dataXYZs.get(i).getX());
                        Double y = Double.valueOf(dataXYZs.get(i).getY());
                        Double z = Double.valueOf(dataXYZs.get(i).getZ());

                        seriesX.appendData(new DataPoint(time, x),true,10);
                        seriesY.appendData(new DataPoint(time, y),true,10);
                        seriesZ.appendData(new DataPoint(time, z),true,10);
                    }
                }
                seriesX.setColor(Color.YELLOW);
                seriesY.setColor(Color.RED);
                seriesZ.setColor(Color.BLACK);

                graph.addSeries(seriesX);
                graph.addSeries(seriesY);
                graph.addSeries(seriesZ);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
