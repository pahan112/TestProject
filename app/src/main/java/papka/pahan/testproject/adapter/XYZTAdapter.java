package papka.pahan.testproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import papka.pahan.testproject.R;
import papka.pahan.testproject.data.DataXYZ;

/**
 * Created by admin on 18.06.2017.
 */

public class XYZTAdapter extends RecyclerView.Adapter<XYZTAdapter.XYZTViewHolder> {

    private List<DataXYZ> mDataXYZs = new ArrayList<>();

    public XYZTAdapter(List<DataXYZ> mDataXYZs) {
        this.mDataXYZs = mDataXYZs;
    }

    @Override
    public XYZTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new XYZTViewHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.list_xzyt, parent, false)));
    }

    @Override
    public void onBindViewHolder(XYZTViewHolder holder, int position) {
        holder.bind(mDataXYZs.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataXYZs.size();
    }

    public class XYZTViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView timeTextView;
        @BindView(R.id.tv_x)
        TextView xTextView;
        @BindView(R.id.tv_y)
        TextView yTextView;
        @BindView(R.id.tv_z)
        TextView zTextView;

        public XYZTViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(DataXYZ dataXYZ) {
            timeTextView.setText("Time: " + dataXYZ.getTime());
            xTextView.setText("X: " + dataXYZ.getX());
            yTextView.setText("Y: " + dataXYZ.getY());
            zTextView.setText("Z: " + dataXYZ.getZ());
        }
    }
}
