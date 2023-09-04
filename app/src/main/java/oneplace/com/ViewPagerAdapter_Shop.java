package oneplace.com;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter_Shop extends FragmentPagerAdapter {
    int tabCount;

    public ViewPagerAdapter_Shop(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Place_Shop_A.newInstance();
            case 1:
                return Place_Shop_B.newInstance();
            case 2:
                return Place_Shop_C.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
