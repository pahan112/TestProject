package papka.pahan.testproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import papka.pahan.testproject.fragment.MyListFragment;
import papka.pahan.testproject.fragment.ScheduleFragment;
/**
 * Created by admin on 18.06.2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private String[] mTableTitles;

    public MyFragmentPagerAdapter(FragmentManager fm,String[] mTableTitles) {
        super(fm);
        this.mTableTitles = mTableTitles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MyListFragment();
            case 1:
                return new ScheduleFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTableTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTableTitles[position];
    }
}
