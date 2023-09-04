package oneplace.com;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Place_Shop_B extends Fragment {

    public Place_Shop_B() {
        // Required empty public constructor
    }
    public static Place_Shop_B newInstance() {
        Place_Shop_B fragment = new Place_Shop_B();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.place_shop_view, container, false);
    }
}