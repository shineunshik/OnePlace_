package oneplace.com;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Place_FreeBoard_B extends Fragment {
    View view;

    public Place_FreeBoard_B() {
        // Required empty public constructor
    }
    public static Place_FreeBoard_B newInstance() {
        Place_FreeBoard_B fragment = new Place_FreeBoard_B();
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
        view = inflater.inflate(R.layout.place_freeboard_b, container, false);
        return view;
    }
}