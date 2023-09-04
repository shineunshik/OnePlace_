package oneplace.com;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Place_Shop_C extends Fragment {

    public Place_Shop_C() {
        // Required empty public constructor
    }

    public static Place_Shop_C newInstance() {
        Place_Shop_C fragment = new Place_Shop_C();
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