package com.example.a19454.biyesheji;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Contact contact=null;
    private List contacts=null;
    private Service service=null;

    private SideLetterBar sideLetterBar;
    private ListView lv_contact;
    private ListView lv_alphabet;
    private TextView tv_alphabet;
    private TextView tv_notice;
    private ClearEditText et_clear;
    private List<String> alphabetList;
    RelativeLayout rel_notice;
    ContactAdapter adapter ;
    private String[] data = new String[] {
            "15129372345","15129372334","15129372335","15129372343","15129372347","151293723423",

    };

    private ArrayList<ContactBean> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_main_activity);//设置窗口标题
        contactList = dataList();
        //对数据进行排序
        initView();
        initEvent();
        initData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//载入主界面菜单控件
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//菜单点击事件
        int id = item.getItemId();
        if (id == R.id.main_add) { // 添加联系人
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.main_more) {  // 弹出菜单
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void initView() {
        sideLetterBar = (SideLetterBar) findViewById(R.id.sideLetterBar);
        lv_contact = (ListView) findViewById(R.id.lv_contact);
        lv_alphabet = (ListView) findViewById(R.id.lv_alphabet);
        tv_notice = (TextView) findViewById(R.id.tv_notice);
        rel_notice = (RelativeLayout) findViewById(R.id.rel_notice);
        et_clear = (ClearEditText) findViewById(R.id.et_clear);
        tv_alphabet = (TextView) findViewById(R.id.tv_alphabet);
        rel_notice.post(new Runnable() {
            @Override
            public void run() {
                tv_notice.getHeight();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rel_notice.getLayoutParams();
                params.height = tv_notice.getHeight()*5;
                params.width = tv_notice.getWidth();
                rel_notice.setLayoutParams(params);
            }
        });

        //my irror log
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    private void initEvent() {
        sideLetterBar.setOnTouchLetterListener(new SideLetterBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                alphabetList.clear();
                ViewPropertyAnimator.animate(rel_notice).alpha(1f).setDuration(0).start();
                //根据当前触摸的字母，去集合中找那个item的首字母和letter一样，然后将对应的item放到屏幕顶端
                for (int i = 0; i < contactList.size(); i++) {
                    String firstAlphabet = contactList.get(i).getPinyin().charAt(0)+"";
                    if(letter.equals(firstAlphabet)){
                        lv_contact.setSelection(i);
                        rel_notice.setVisibility(View.VISIBLE);
                        break;
                    }
                    if(letter.equals("#")){
                        lv_contact.setSelection(0);
                        rel_notice.setVisibility(View.GONE);
                    }
                }
                for (int i = 0; i < contactList.size(); i++) {
                    String firstAlphabet = contactList.get(i).getPinyin().toString().trim().charAt(0)+"";

                    if(letter.equals(firstAlphabet)){
                        //说明找到了，那么应该讲当前的item放到屏幕顶端
                        tv_notice.setText(letter);
                        if(!alphabetList.contains(String.valueOf(contactList.get(i).getName().trim().charAt(0)))){
                            alphabetList.add(String.valueOf(contactList.get(i).getName().trim().charAt(0)));
                        }
                    }

                }
                showCurrentWord(letter);
                //显示当前触摸的字母

                AlphabetAdp alphabetAdp = new AlphabetAdp(MainActivity.this,alphabetList);
                lv_alphabet.setAdapter(alphabetAdp);
                alphabetAdp.notifyDataSetChanged();
            }
        });


        lv_contact.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    //如果垂直滑动，则需要关闭已经打开的layout
                    SwipeManager.getInstance().closeCurrentLayout();
                }

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int pos = lv_contact.getFirstVisiblePosition();
                if (contactList.size()>0){
                    tv_alphabet.setVisibility(View.VISIBLE);
                    String text = contactList.get(pos).getPinyin().charAt(0)+"";
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m1 = p.matcher(text);
                    if(m1.matches()){
                        tv_alphabet.setText("#");
                    }else {
                        tv_alphabet.setText(text);
                    }
                }else {
                    tv_alphabet.setVisibility(View.GONE);
                }
            }
        });
        et_clear.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                fuzzySearch(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lv_alphabet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String alphabet = alphabetList.get(position).trim();
                setIsVisiable();
                for (int i = 0;i<contactList.size();i++){
                    if (alphabet.equals(String.valueOf(contactList.get(i).getName().trim().charAt(0)))){
                        int pos = i%lv_contact.getChildCount();
                        int childCount = lv_contact.getChildCount();
                        if(position==0&&pos-position==1||childCount-pos==1){
                            lv_contact.setSelection(i);
                        }else {
                            lv_contact.setSelection(i-1);
                        }
                        break;
                    }
                }
            }
        });
    }
    private void initData() {

        //3.设置Adapter
        adapter = new ContactAdapter(this,contactList);
        lv_contact.setAdapter(adapter);
        alphabetList = new ArrayList<>();
    }

    protected void showCurrentWord(String letter) {
        tv_notice.setText(letter);
        setIsVisiable();
    }
    private Handler handler = new Handler();
    private void setIsVisiable(){
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(rel_notice).alpha(0f).setDuration(1000).start();
            }
        }, 4000);
    }
    private ArrayList <ContactBean> dataList() {
        // 虚拟数据
        ArrayList <ContactBean> mSortList = new ArrayList<ContactBean>();
        for(int i=0;i<data.length;i++){
            ContactBean bean = new ContactBean(data[i]);
            bean.pinYinStyle = parsePinYinStyle(data[i]);
            mSortList.add(bean);
        }
        Collections.sort(mSortList);
        return mSortList;
    }
    private void fuzzySearch(String str) {
        ArrayList<ContactBean> filterDateList = new ArrayList<ContactBean>();
        // 虚拟数据
        if (TextUtils.isEmpty(str)){
            sideLetterBar.setVisibility(View.VISIBLE);
            filterDateList = dataList();
        }else {
            filterDateList.clear();
            sideLetterBar.setVisibility(View.GONE);
            for(ContactBean contactBean : dataList()){
                String name = contactBean.getName();
                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(str);
                if(m.matches()){
                    str = PinYinUtil.getPinyin(str);
                }
                if(PinYinUtil.getPinyin(name).contains(str.toUpperCase())|| contactBean.pinYinStyle.briefnessSpell.toUpperCase().contains(str.toUpperCase())
                        || contactBean.pinYinStyle.completeSpell.toUpperCase().contains(str.toUpperCase())){
                    filterDateList.add(contactBean);
                }
            }
        }
        contactList = filterDateList;
        adapter = new ContactAdapter(this,filterDateList);
        lv_contact.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public PinYinStyle parsePinYinStyle(String content) {
        PinYinStyle pinYinStyle = new PinYinStyle();
        if (content != null && content.length() > 0) {
            //其中包含的中文字符
            String[] enStrs = new String[content.length()];
            for (int i=0;i<content.length();i++){
                enStrs[i] = PinYinUtil.getPinyin(String.valueOf(content.charAt(i)));
            }
            for (int i = 0, length = enStrs.length; i < length; i++) {
                if (enStrs[i].length() > 0) {
                    //拼接简拼
                    pinYinStyle.briefnessSpell += enStrs[i].charAt(0);
                }
            }
        }
        return pinYinStyle;
    }

    private void getContent(){

    }
}
