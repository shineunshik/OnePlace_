package oneplace.com;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Place_FreeBoard_A extends Fragment {
    View view;
    SwipeRefreshLayout swp;
    RecyclerView recyclerview;
    ArrayList<Ob_FreeBoard> arrayList;
    RecyclerView.Adapter adapter;
    Button write;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Bundle bundle;
    int position;
    String select_address;
    public Place_FreeBoard_A() {
        // Required empty public constructor
    }
    public static Place_FreeBoard_A newInstance() {
        Place_FreeBoard_A fragment = new Place_FreeBoard_A();
//        Bundle bundle =new Bundle();
//        bundle.putString("board_address",board_address);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_freeboard_a, container, false);
     //   bundle = getArguments();
        get_save_select_address();
        //리프레쉬
        swp=(SwipeRefreshLayout)view.findViewById(R.id.swp);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh_Start();
                swp.setRefreshing(false);

            }
        });

        //게시판 글 리스트
        recyclerview=(RecyclerView)view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter= new CusTomAdapter_FreeBoard(arrayList,getActivity());
        recyclerview.setAdapter(adapter);

        database=FirebaseDatabase.getInstance("https://oneplace-db16a-default-rtdb.firebaseio.com/");
        databaseReference=database.getReference("-자유 게시판").child(select_address).child("A");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Ob_FreeBoard ob_freeBoard = snapshot.getValue(Ob_FreeBoard.class);
                arrayList.add(ob_freeBoard);
                adapter.notifyDataSetChanged();
                recyclerview.scrollToPosition(adapter.getItemCount()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                Ob_FreeBoard ob_freeBoard_change = snapshot.getValue(Ob_FreeBoard.class); //변경된 데이터

                position =0;
                for (Ob_FreeBoard ob_freeBoard : arrayList){ //arraylist에 들어있는 데이터를 fro문으로 돌려서 변경된 키값과 비교하여 변경된 항목을 찾는다
                    if (ob_freeBoard.getF_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        arrayList.get(position).setF_board_view(ob_freeBoard_change.getF_board_view()); //변경된 데이터를 넣는다
                        arrayList.get(position).setF_board_review(ob_freeBoard_change.getF_board_review());
                    }
                    position++;
                }
                adapter.notifyDataSetChanged(); //데이터 변경 및 저장
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String snap_key = snapshot.getKey(); //데이터가 변경된 항목의 키값
                //ConcurrentModificationException 오류로 인한 처리 코드 for문안에서 데이터 삭제해서 나는 오류라고 함
                for(Iterator<Ob_FreeBoard> itr = arrayList.iterator(); itr.hasNext();){
                    Ob_FreeBoard ob_freeBoard = itr.next();
                    if (ob_freeBoard.getF_key().equals(snap_key)){ //변경된 데이터의 키값과 일치하는지 확인
                        itr.remove();
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        write = (Button) view.findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Place_FreeBoard_Write.class);
                intent.putExtra("board_type","A"); //9.A
                startActivity(intent);
            }
        });


        return  view;
    }

    public void Refresh_Start(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    arrayList.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        arrayList.add(snapshot.getValue(Ob_FreeBoard.class));
                    }
                }
                catch (NullPointerException nullPointerException){
                    Toast.makeText(getActivity(),"null",Toast.LENGTH_SHORT).show();
                }
                recyclerview.scrollToPosition(adapter.getItemCount()-1);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter= new CusTomAdapter_FreeBoard(arrayList,getActivity()); //adapter2를 만들어서 새로고침되는 리스트는 따로 관리
        recyclerview.setAdapter(adapter);
    }

    public void set_save_select_address(){
        SharedPreferences set_save_select_address = getActivity().getSharedPreferences("save_select_address",MODE_PRIVATE);
        SharedPreferences.Editor set_save_select_address_editor =set_save_select_address.edit();
        set_save_select_address_editor.putString("save_select_address_key",select_address);
        set_save_select_address_editor.apply();
    }
    public void get_save_select_address(){
        SharedPreferences get_save_select_address = getActivity().getSharedPreferences("save_select_address",MODE_PRIVATE);
        select_address = get_save_select_address.getString("save_select_address_key","");
    }
}