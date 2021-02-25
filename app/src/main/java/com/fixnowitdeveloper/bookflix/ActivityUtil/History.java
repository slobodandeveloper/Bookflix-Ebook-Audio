package com.fixnowitdeveloper.bookflix.ActivityUtil;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fixnowitdeveloper.bookflix.AdapterUtil.HomePagerAdapter;
import com.fixnowitdeveloper.bookflix.ConstantUtil.Constant;
import com.fixnowitdeveloper.bookflix.FragmentUtil.ReadBookType;
import com.fixnowitdeveloper.bookflix.ObjectUtil.PagerObject;
import com.fixnowitdeveloper.bookflix.R;
import com.fixnowitdeveloper.bookflix.Utility.Utility;

import java.util.ArrayList;

public class History extends AppCompatActivity implements View.OnClickListener {
    private String TAG = History.class.getName();
    private TextView txtMenu;
    private ImageView imageBack;
    private ImageView imageSearch;
    private TabLayout layoutTab;
    private ViewPager pagerFragment;
    private HomePagerAdapter pagerAdapter;
    private String category;
    private String categoryId;
    private ArrayList<PagerObject> objectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utility.changeAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list);

        initUI(); //Initialize UI

    }


    /**
     * <p>It initialize the UI</p>
     */
    private void initUI() {

        Utility.Logger(TAG, "Status = Category " + category + " Category Id " + categoryId);

        txtMenu = findViewById(R.id.txt_menu);
        txtMenu.setText(Utility.getStringFromRes(this, R.string.history));

        imageBack = findViewById(R.id.image_back);
        imageBack.setVisibility(View.VISIBLE);
        imageBack.setImageResource(R.drawable.ic_back);

        //Adding Fragment into Pager Adapter MUESTRA LA OPCION PDF DENTRO DEL LIBRO

        /* objectArrayList.add(new PagerObject()
                .setTitle(Utility.getStringFromRes(this, R.string.pdf))
                .setFragment(ReadBookType.getFragmentInstance(Constant.DataType.PDF))); */

        objectArrayList.add(new PagerObject()
                .setTitle(Utility.getStringFromRes(this, R.string.epub))
                .setFragment(ReadBookType.getFragmentInstance(Constant.DataType.EPUB)));

        //Initialize Pager Adapter

        pagerFragment = (ViewPager) findViewById(R.id.pager_fragment);
        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), objectArrayList);
        pagerFragment.setAdapter(pagerAdapter);

        //Setup Tab Layout with Pager Fragment

        layoutTab = (TabLayout) findViewById(R.id.layout_tab);
        layoutTab.setupWithViewPager(pagerFragment);

        for (int i = 0; i < layoutTab.getTabCount(); i++) {

            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_item_layout, null);
            //tv.setTextColor(Utility.getColourFromRes(this, R.color.));
            tv.setText(objectArrayList.get(i).getTitle());
            layoutTab.getTabAt(i).setCustomView(tv);

        }

        imageBack.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == imageBack) {
            finish();
        }
    }

}
