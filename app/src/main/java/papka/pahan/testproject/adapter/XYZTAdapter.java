package papka.pahan.testproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 18.06.2017.
 */

public class XYZTAdapter extends RecyclerView.Adapter<XYZTAdapter.XYZTViewHolder> {

    public XYZTAdapter(){

    }

    @Override
    public XYZTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(XYZTViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class XYZTViewHolder extends RecyclerView.ViewHolder{

        public XYZTViewHolder(View itemView) {
            super(itemView);
        }
    }
}
