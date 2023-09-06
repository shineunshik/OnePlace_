package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CusTomAdapter_Bus_Station_list extends RecyclerView.Adapter<CusTomAdapter_Bus_Station_list.CustomViewHolder> {
    ArrayList<Ob_Bus_Station_list> arrayList;
    Context context;
    String select_address;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String day_save;
    public CusTomAdapter_Bus_Station_list(ArrayList<Ob_Bus_Station_list> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_station_list_form,parent,false);
        CusTomAdapter_Bus_Station_list.CustomViewHolder customViewHolder = new CusTomAdapter_Bus_Station_list.CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        get_save_select_address();





//        if (position<9){
//            holder.address.setText("0"+(position+1)+".  "+arrayList.get(position).getAddress());
//        }else {
//            holder.address.setText(position+1+".  "+arrayList.get(position).getAddress());
//        }

        if (position<9){
            holder.address.setText("0"+(position)+".  "+arrayList.get(position).getAddress());
        }else {
            holder.address.setText(position+".  "+arrayList.get(position).getAddress());
        }
        holder.bus_station_number.setText("ID : "+arrayList.get(position).getTerminalId());
        holder.bus_station_name.setText("정류소 명 : "+arrayList.get(position).getTerminalNm());

//        try {
//            database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
//            databaseReference=database.getReference("-고속버스").child("-정류장LIST");
//
//            for (int num = 0; num <arrayList.size(); num++){
//                if (arrayList.get(num).getTerminalNm().contains("서울")|arrayList.get(num).getTerminalNm().contains("상봉")){
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("서울");
//                }
//                else if (arrayList.get(num).getTerminalNm().contains("수원")|arrayList.get(num).getTerminalNm().contains("성남")
//                        |arrayList.get(num).getTerminalNm().contains("고양")|arrayList.get(num).getTerminalNm().contains("광명")
//                        |arrayList.get(num).getTerminalNm().contains("안산")|arrayList.get(num).getTerminalNm().contains("안성")
//                        |arrayList.get(num).getTerminalNm().contains("용인")|arrayList.get(num).getTerminalNm().contains("여주")
//                        |arrayList.get(num).getTerminalNm().contains("이천")|arrayList.get(num).getTerminalNm().contains("인천")
//                        |arrayList.get(num).getTerminalNm().contains("죽전")|arrayList.get(num).getTerminalNm().contains("평택")
//                        |arrayList.get(num).getTerminalNm().contains("부천")|arrayList.get(num).getTerminalNm().contains("의정부")
//                        |arrayList.get(num).getTerminalNm().contains("포천")|arrayList.get(num).getTerminalNm().contains("구리")
//                        |arrayList.get(num).getTerminalNm().contains("시흥")|arrayList.get(num).getTerminalNm().contains("신갈")
//                        |arrayList.get(num).getTerminalNm().contains("안중")|arrayList.get(num).getTerminalNm().contains("영통")
//                        |arrayList.get(num).getTerminalNm().contains("오산")
//
//                        )
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("경기도");
//                }
//                else if (arrayList.get(num).getTerminalNm().contains("강릉")|arrayList.get(num).getTerminalNm().contains("강원")
//                        |arrayList.get(num).getTerminalNm().contains("속초")
//                        |arrayList.get(num).getTerminalNm().contains("양양")|arrayList.get(num).getTerminalNm().contains("원주")
//                        |arrayList.get(num).getTerminalNm().contains("철원")|arrayList.get(num).getTerminalNm().contains("횡성")
//                        |arrayList.get(num).getTerminalNm().contains("춘천")|arrayList.get(num).getTerminalNm().contains("동해")
//                        |arrayList.get(num).getTerminalNm().contains("삼척")|arrayList.get(num).getTerminalNm().contains("영월"))
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("강원도");
//                }
//                else if (arrayList.get(num).getTerminalNm().contains("천안")|arrayList.get(num).getTerminalNm().contains("대전")
//                        |arrayList.get(num).getTerminalNm().contains("세종") |arrayList.get(num).getTerminalNm().contains("아산")
//                        |arrayList.get(num).getTerminalNm().contains("정안")|arrayList.get(num).getTerminalNm().contains("태안")
//                        |arrayList.get(num).getTerminalNm().contains("홍성")|arrayList.get(num).getTerminalNm().contains("청양")
//                        |arrayList.get(num).getTerminalNm().contains("탕정")|arrayList.get(num).getTerminalNm().contains("창기리")
//                        |arrayList.get(num).getTerminalNm().contains("조치원")|arrayList.get(num).getTerminalNm().contains("공주")
//                        |arrayList.get(num).getTerminalNm().contains("금산")|arrayList.get(num).getTerminalNm().contains("내포")
//                        |arrayList.get(num).getTerminalNm().contains("논산")|arrayList.get(num).getTerminalNm().contains("당진")
//                        |arrayList.get(num).getTerminalNm().contains("배방")|arrayList.get(num).getTerminalNm().contains("보령")
//                        |arrayList.get(num).getTerminalNm().contains("서산")|arrayList.get(num).getTerminalNm().contains("선문대")
//                        |arrayList.get(num).getTerminalNm().contains("안면도")|arrayList.get(num).getTerminalNm().contains("정산")
//                        |arrayList.get(num).getTerminalNm().contains("인삼랜드")|arrayList.get(num).getTerminalNm().contains("유성")
//                        |arrayList.get(num).getTerminalNm().contains("예산")
//                )
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("충청남도");
//                }
//
//                else if (arrayList.get(num).getTerminalNm().contains("청주")|arrayList.get(num).getTerminalNm().contains("황간")
//                        |arrayList.get(num).getTerminalNm().contains("흥덕")|arrayList.get(num).getTerminalNm().contains("충주")
//                        |arrayList.get(num).getTerminalNm().contains("증평")|arrayList.get(num).getTerminalNm().contains("중앙탑면")
//                        |arrayList.get(num).getTerminalNm().contains("제천")|arrayList.get(num).getTerminalNm().contains("괴산")
//                        |arrayList.get(num).getTerminalNm().contains("덕산")|arrayList.get(num).getTerminalNm().contains("보은")
//                        |arrayList.get(num).getTerminalNm().contains("북부오창")|arrayList.get(num).getTerminalNm().contains("속리산")
//                        |arrayList.get(num).getTerminalNm().contains("운천")|arrayList.get(num).getTerminalNm().contains("연무대"))
//
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("충청북도");
//                }
//
//                else if (arrayList.get(num).getTerminalNm().contains("전주")|arrayList.get(num).getTerminalNm().contains("태인")
//                        |arrayList.get(num).getTerminalNm().contains("진안")|arrayList.get(num).getTerminalNm().contains("고창")
//                        |arrayList.get(num).getTerminalNm().contains("군산")|arrayList.get(num).getTerminalNm().contains("김제")
//                        |arrayList.get(num).getTerminalNm().contains("남원")|arrayList.get(num).getTerminalNm().contains("덕과")
//                        |arrayList.get(num).getTerminalNm().contains("봉동")|arrayList.get(num).getTerminalNm().contains("부안")
//                        |arrayList.get(num).getTerminalNm().contains("순창")|arrayList.get(num).getTerminalNm().contains("애통리")
//                        |arrayList.get(num).getTerminalNm().contains("정읍")|arrayList.get(num).getTerminalNm().contains("자치인재원")
//                        |arrayList.get(num).getTerminalNm().contains("익산")|arrayList.get(num).getTerminalNm().contains("전북혁신")
//                        |arrayList.get(num).getTerminalNm().contains("전북강진")
//                )
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("전라북도");
//                }
//
//                else if (arrayList.get(num).getTerminalNm().contains("광주")|arrayList.get(num).getTerminalNm().contains("나주")
//                        |arrayList.get(num).getTerminalNm().contains("함평")|arrayList.get(num).getTerminalNm().contains("해제")
//                        |arrayList.get(num).getTerminalNm().contains("해남")|arrayList.get(num).getTerminalNm().contains("화순")
//                        |arrayList.get(num).getTerminalNm().contains("회진")|arrayList.get(num).getTerminalNm().contains("진도")
//                        |arrayList.get(num).getTerminalNm().contains("지도")|arrayList.get(num).getTerminalNm().equals("강진")
//                        |arrayList.get(num).getTerminalNm().contains("고흥")|arrayList.get(num).getTerminalNm().contains("곡성")
//                        |arrayList.get(num).getTerminalNm().contains("관산")|arrayList.get(num).getTerminalNm().contains("광양")
//                        |arrayList.get(num).getTerminalNm().contains("구례")|arrayList.get(num).getTerminalNm().contains("남악")
//                        |arrayList.get(num).getTerminalNm().contains("녹동")|arrayList.get(num).getTerminalNm().contains("능주")
//                        |arrayList.get(num).getTerminalNm().contains("담양")|arrayList.get(num).getTerminalNm().contains("목포")
//                        |arrayList.get(num).getTerminalNm().contains("무안")|arrayList.get(num).getTerminalNm().contains("문장")
//                        |arrayList.get(num).getTerminalNm().contains("벌교")|arrayList.get(num).getTerminalNm().contains("보성")
//                        |arrayList.get(num).getTerminalNm().contains("섬진강")|arrayList.get(num).getTerminalNm().contains("여수")
//                        |arrayList.get(num).getTerminalNm().contains("장흥")|arrayList.get(num).getTerminalNm().contains("장성")
//                        |arrayList.get(num).getTerminalNm().contains("임자")|arrayList.get(num).getTerminalNm().contains("원동")
//                        |arrayList.get(num).getTerminalNm().contains("완도")|arrayList.get(num).getTerminalNm().contains("옥과")
//                        |arrayList.get(num).getTerminalNm().contains("영암")|arrayList.get(num).getTerminalNm().contains("영산포")
//                        |arrayList.get(num).getTerminalNm().contains("영광")|arrayList.get(num).getTerminalNm().contains("여천")
//                         |arrayList.get(num).getTerminalNm().contains("순천")
//                )
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("전라남도");
//                }
//
//
//
//                else if (arrayList.get(num).getTerminalNm().contains("구미")|arrayList.get(num).getTerminalNm().contains("경북도청")
//                        |arrayList.get(num).getTerminalNm().contains("경주")|arrayList.get(num).getTerminalNm().contains("김천")
//                        |arrayList.get(num).getTerminalNm().contains("김천혁신")|arrayList.get(num).getTerminalNm().contains("대구")
//                        |arrayList.get(num).getTerminalNm().contains("점촌")|arrayList.get(num).getTerminalNm().contains("예천")
//                        |arrayList.get(num).getTerminalNm().contains("영주")|arrayList.get(num).getTerminalNm().contains("영천")
//                        |arrayList.get(num).getTerminalNm().contains("포항")|arrayList.get(num).getTerminalNm().contains("경북도청")
//                        |arrayList.get(num).getTerminalNm().contains("풍기")|arrayList.get(num).getTerminalNm().contains("낙동강")
//                        |arrayList.get(num).getTerminalNm().contains("내서")|arrayList.get(num).getTerminalNm().contains("상주")
//                        |arrayList.get(num).getTerminalNm().contains("선산")|arrayList.get(num).getTerminalNm().contains("안동")
//                        |arrayList.get(num).getTerminalNm().contains("영덕"))
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("경상북도");
//                }
//                else if (arrayList.get(num).getTerminalNm().contains("부산")|arrayList.get(num).getTerminalNm().contains("김해")
//                        |arrayList.get(num).getTerminalNm().contains("창원")|arrayList.get(num).getTerminalNm().contains("통영")
//                        |arrayList.get(num).getTerminalNm().contains("진해")|arrayList.get(num).getTerminalNm().contains("진주")
//                        |arrayList.get(num).getTerminalNm().contains("창원")|arrayList.get(num).getTerminalNm().contains("마산")
//                        |arrayList.get(num).getTerminalNm().contains("울산")|arrayList.get(num).getTerminalNm().contains("서부산"))
//                {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("경상남도");
//                }
//
//                else {
//                    databaseReference.child(arrayList.get(num).getTerminalNm()).child("address").setValue("");
//                }
//            }
//        }
//        catch (NullPointerException nullPointerException){
//
//        }




    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView bus_station_name;
        TextView bus_station_number;
        TextView address;

        View view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bus_station_number = itemView.findViewById(R.id.bus_station_number);
            this.bus_station_name = itemView.findViewById(R.id.bus_station_name);
            this.address = itemView.findViewById(R.id.address);
            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    get_save_set_save_day();
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context, Place_Bus_Station_Info.class);
                    intent.putExtra("terminalId",arrayList.get(position).getTerminalId());
                    intent.putExtra("terminalNm",arrayList.get(position).getTerminalNm());
                    intent.putExtra("day_save",day_save);
                    context.startActivity(intent);
                }
            });


        }
    }

    public void get_save_select_address(){
        SharedPreferences get_save_select_address = context.getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }





    public void set_save_day(){
        SharedPreferences set_save_day = context.getSharedPreferences("save_save_day",MODE_PRIVATE);
        SharedPreferences.Editor set_save_day_editor =set_save_day.edit();
        set_save_day_editor.putString("save_day_key",day_save);
        set_save_day_editor.apply();
    }
    public void get_save_set_save_day(){
        SharedPreferences get_save_day = context.getSharedPreferences("save_save_day",MODE_PRIVATE);
        day_save = get_save_day.getString("save_day_key","");
    }



}
