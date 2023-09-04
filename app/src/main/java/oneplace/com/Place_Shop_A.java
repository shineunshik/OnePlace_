package oneplace.com;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Place_Shop_A extends Fragment {
    View view;
    SearchView search_view;
    SwipeRefreshLayout swp;
    RecyclerView recyclerview;
    ArrayList<Ob_Client> arrayList;
    RecyclerView.Adapter adapter;

    public Place_Shop_A() {
        // Required empty public constructor
    }
    public static Place_Shop_A newInstance() {
        Place_Shop_A fragment = new Place_Shop_A();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.place_shop_view, container, false);

        //검색
        search_view = (SearchView)view.findViewById(R.id.search_view);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String  text) {
                setSearch_view(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                setSearch_view(text);
                return false;
            }
        });
        //리프레쉬
        swp=(SwipeRefreshLayout)view.findViewById(R.id.swp);
        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Shuffle();
                swp.setRefreshing(false);
            }
        });

        //업체 리스트
        recyclerview=(RecyclerView)view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();

        arrayList.add(new Ob_Client("성남","010-4152-3197","성남",""));
        arrayList.add(new Ob_Client("잠실","010-4152-3197","잠실",""));
        arrayList.add(new Ob_Client("서울","010-4152-3197","서울",""));


        adapter= new CusTomAdapter_Client(arrayList,getActivity());
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    public void Shuffle(){
        try {
            Collections.shuffle(arrayList,new Random(System.currentTimeMillis()));
            adapter = new CusTomAdapter_Client(arrayList,getActivity());
            recyclerview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        catch (RuntimeException e){
            Toast.makeText(getActivity(),"셔플 오류",Toast.LENGTH_SHORT).show();
        }
    }

    public void  setSearch_view(String charText){
        ArrayList<Ob_Client> myList = new ArrayList<>();
        for (Ob_Client ob_client : arrayList){
            if (ob_client.getC_name().toLowerCase().contains(charText.toLowerCase())||ob_client.getC_address().toLowerCase().contains(charText.toLowerCase())){
                myList.add(ob_client);
            }
        }
        adapter = new CusTomAdapter_Client(myList,getActivity());
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}