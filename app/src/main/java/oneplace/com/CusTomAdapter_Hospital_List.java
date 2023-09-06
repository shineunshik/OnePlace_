package oneplace.com;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CusTomAdapter_Hospital_List extends RecyclerView.Adapter<CusTomAdapter_Hospital_List.CustomViewHolder> {

    ArrayList<Ob_Hospital_list> arrayList;
    Context context;

    public CusTomAdapter_Hospital_List(ArrayList<Ob_Hospital_list> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CusTomAdapter_Hospital_List.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.host_list_form,parent,false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CusTomAdapter_Hospital_List.CustomViewHolder holder, int position) {

        holder.dutyName.setText(position+1+".  "+arrayList.get(position).getDutyName());
        holder.dutyTel1.setText("대표전화 : "+arrayList.get(position).getDutyTel1());
        holder.dutyTel3.setText("응급실 : "+arrayList.get(position).getDutyTel3());
        holder.dutyAddr.setText(arrayList.get(position).getDutyAddr());
        holder.hpid.setText(arrayList.get(position).getHpid());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView dutyName;
        TextView dutyTel1;
        TextView dutyTel3;
        TextView dutyAddr;
        TextView hpid;
        View view;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.dutyName = itemView.findViewById(R.id.dutyName);
            this.dutyTel1 = itemView.findViewById(R.id.dutyTel1);
            this.dutyTel3 = itemView.findViewById(R.id.dutyTel3);
            this.dutyAddr = itemView.findViewById(R.id.dutyAddr);
            this.hpid = itemView.findViewById(R.id.hpid);
            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getLayoutPosition();
                    Intent intent = new Intent(context, Place_Hospital_Info.class);
                    intent.putExtra("dutyName",arrayList.get(position).getDutyName());
                    intent.putExtra("dutyAddr",arrayList.get(position).getDutyAddr());
                    intent.putExtra("dutyTel1",arrayList.get(position).getDutyTel1());
                    intent.putExtra("dutyTel3",arrayList.get(position).getDutyTel3());
                    intent.putExtra("dutyEmclsName",arrayList.get(position).getDutyEmclsName());
                    intent.putExtra("dutyEmcls",arrayList.get(position).getDutyEmcls());
                    intent.putExtra("hpid",arrayList.get(position).getHpid());
                    intent.putExtra("phpid",arrayList.get(position).getPhpid());

                    context.startActivity(intent);


                }
            });
        }
    }
}
