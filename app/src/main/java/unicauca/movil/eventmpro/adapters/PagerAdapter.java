package unicauca.movil.eventmpro.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by RicardoM on 22/11/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> data;

    public PagerAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int dia = position+1;
        return "Dia "+dia;
    }

}
