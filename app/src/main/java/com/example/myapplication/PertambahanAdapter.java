package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class PertambahanAdapter extends RecyclerView.Adapter<PertambahanAdapter.PertambahanViewHolder> {

    private List<Pertambahan> pertambahanList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public PertambahanAdapter(List<Pertambahan> pertambahanList) {
        this.pertambahanList = pertambahanList;
    }

    public class PertambahanViewHolder extends RecyclerView.ViewHolder {
        public TextView first, second, operator, result;

        public PertambahanViewHolder(View itemView) {
            super(itemView);
            first = (TextView) itemView.findViewById(R.id.first);
            second = (TextView) itemView.findViewById(R.id.second);
            operator = (TextView) itemView.findViewById(R.id.operator);
            result = (TextView) itemView.findViewById(R.id.result);
        }
    }

    @Override
    public PertambahanViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_pertambahan, parent, false);

        return new PertambahanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PertambahanViewHolder holder, int position) {
        Pertambahan pertambahan = pertambahanList.get(position);
        holder.first.setText(pertambahan.getFirst().toString());
        holder.operator.setText(pertambahan.getOperator());
        holder.second.setText(pertambahan.getSecond().toString());
        holder.result.setText(pertambahan.getResult().toString());
    }

    @Override
    public int getItemCount() {
        return pertambahanList.size();
    }
}
