package oneplace.com;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter_FreeBoard extends FragmentPagerAdapter {
    int tabCount;

    String board_address;
    public ViewPagerAdapter_FreeBoard(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Place_FreeBoard_A.newInstance();
            case 1:
                return Place_FreeBoard_B.newInstance();
            case 2:
                return Place_FreeBoard_C.newInstance();
            case 3:
                return Place_FreeBoard_D.newInstance();
            case 4:
                return Place_FreeBoard_E.newInstance();
            case 5:
                return Place_FreeBoard_F.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

//    public void getText(String board_address){
//        this.board_address = board_address;
//    }
}
