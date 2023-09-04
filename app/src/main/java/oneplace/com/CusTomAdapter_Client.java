package oneplace.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CusTomAdapter_Client extends RecyclerView.Adapter<CusTomAdapter_Client.CustomViewHolder> {

    ArrayList<Ob_Client> arrayList;
    Context context;

    public CusTomAdapter_Client(ArrayList<Ob_Client> arrayList,Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CusTomAdapter_Client.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_form,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CusTomAdapter_Client.CustomViewHolder holder, int position) {


        Glide.with(holder.itemView)
                .load(arrayList.get(position).getC_image())
                .into(holder.c_image);

        holder.c_name.setText(arrayList.get(position).getC_name());
        holder.c_address.setText(arrayList.get(position).getC_address());
        holder.c_phone.setText(arrayList.get(position).getC_phone());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView c_image;
        TextView c_name;
        TextView c_address;
        TextView c_phone;
        View view;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.c_image = itemView.findViewById(R.id.c_image);
            this.c_name = itemView.findViewById(R.id.c_name);
            this.c_address = itemView.findViewById(R.id.c_address);
            this.c_phone = itemView.findViewById(R.id.c_phone);
            view = itemView;
        }
    }
}
