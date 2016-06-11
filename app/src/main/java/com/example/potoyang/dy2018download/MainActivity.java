package com.example.potoyang.dy2018download;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.potoyang.dy2018download.Regex.getRegexHtml;
import static com.example.potoyang.dy2018download.Regex.getRegexTitle;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerTabStrip tabStrip;

    private List<String> titleList;
    private List<String> listHtml;
    private List<View> viewList;

    String[] tempTitle = null;
    String[] tempHtml = null;
    String content = null;
    int i = 0;

    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //从DATA键中取出值
        Intent intent = getIntent();
        content = intent.getStringExtra("DATA");

        init();
    }

    private void init() {

        listHtml = new ArrayList<>();

        //将得到的字符串以回车分开,并用正则表达式进行解析,然后存到ArrayList中
        tempTitle = getRegexTitle(content).split("\n");
        tempHtml = getRegexHtml(content).split("\n");

        //保证Title与链接对应
        for (i = 0; i < 60; i++) {
            listHtml.add(tempHtml[i]);
        }

        LayoutInflater inflater = getLayoutInflater();
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewList = new ArrayList<>();

        //显示四个Tab页面，并分别设置ListView
        View view1 = inflater.inflate(R.layout.first_layout1, null);
        ListView lv_title1 = (ListView) view1.findViewById(R.id.lv_title1);
        List<String> listTitle1 = new ArrayList<>();
        for (i = 0; i < 15; i++) {
            listTitle1.add(tempTitle[i]);
        }
        adapter = new ListViewAdapter(getBaseContext(), listTitle1);
        lv_title1.setAdapter(adapter);
        lv_title1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("urlStr", listHtml.get(position));
                intent.setClass(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
        viewList.add(view1);

        View view2 = inflater.inflate(R.layout.first_layout2, null);
        ListView lv_title2 = (ListView) view2.findViewById(R.id.lv_title2);
        List<String> listTitle2 = new ArrayList<>();
        for (; i < 30; i++) {
            listTitle2.add(tempTitle[i]);
        }
        ListViewAdapter adapter2 = new ListViewAdapter(getBaseContext(), listTitle2);
        lv_title2.setAdapter(adapter2);
        lv_title2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("urlStr", listHtml.get(position + 15));
                intent.setClass(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
        viewList.add(view2);

        View view3 = inflater.inflate(R.layout.first_layout3, null);
        ListView lv_title3 = (ListView) view3.findViewById(R.id.lv_title3);
        List<String> listTitle3 = new ArrayList<>();
        for (; i < 45; i++) {
            listTitle3.add(tempTitle[i]);
        }
        adapter = new ListViewAdapter(getBaseContext(), listTitle3);
        lv_title3.setAdapter(adapter);
        lv_title3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("urlStr", listHtml.get(position + 30));
                intent.setClass(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
        viewList.add(view3);

        View view4 = inflater.inflate(R.layout.first_layout4, null);
        ListView lv_title4 = (ListView) view4.findViewById(R.id.lv_title4);
        List<String> listTitle4 = new ArrayList<>();
        for (; i < 60; i++) {
            listTitle4.add(tempTitle[i]);
        }
        adapter = new ListViewAdapter(getBaseContext(), listTitle4);
        lv_title4.setAdapter(adapter);
        lv_title4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("urlStr", listHtml.get(position + 45));
                intent.setClass(MainActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
        viewList.add(view4);

        //添加Title
        titleList = new ArrayList<>();
        titleList.add("新片精品");
        titleList.add("必看热片");
        titleList.add("迅雷资源");
        titleList.add("经典大片");

        initViewPager();
    }

    private void initViewPager() {
        viewPager.setAdapter(pagerAdapter);

        // 修改指示器的颜色
        tabStrip = (PagerTabStrip) findViewById(R.id.strip_main);
        tabStrip.setTabIndicatorColor(Color.rgb(63, 72, 204));
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        /**
         * 实例化item
         */
        @Override
        public Object instantiateItem(android.view.ViewGroup container,
                                      int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        /**
         * 销毁item
         */
        @Override
        public void destroyItem(android.view.ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));
        }

        // 重写此方法即可显示标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    };

    /*SQLite数据库操作
    private boolean tableIsExist = false;
    private MyDBHelper myDBHelper = new MyDBHelper(this, "Movie.db", null, 1);
    private SQLiteDatabase db;
    private ContentValues values;
    tableIsExist = myDBHelper.tableIsExist("Movie");
    if (false == tableIsExist) {
        db = myDBHelper.getWritableDatabase();
        values = new ContentValues();
        for (int j = 0; j < tempTitle.length; j++) {
            values.put("name", tempTitle[j]);
            values.put("URL", tempHtml[j]);
            db.insert("Movie", null, values);
            values.clear();
        }
    } else {
        db.update("Movie",);
    }*/

}