package oneplace.com;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Place_FreeBoard_F extends Fragment {
    View view;

    public Place_FreeBoard_F() {
        // Required empty public constructor
    }

    public static Place_FreeBoard_F newInstance() {
        Place_FreeBoard_F fragment = new Place_FreeBoard_F();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.place_freeboard_f, container, false);
        return view;
    }
}