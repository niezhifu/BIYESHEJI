package com.example.a19454.biyesheji;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class AddActivity extends AppCompatActivity {

    private EditText name=null;    // declare EditText
    private EditText phone=null;
    private EditText number=null;
    private EditText qq=null;
    private EditText email=null;
    private EditText address=null;
    private Service service=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle(R.string.title_activity_add);
        service = new Service(this);
        init();  // init
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    // init - get views by Id
    private void init(){
        name = (EditText)findViewById(R.id.add_edt1);
        phone = (EditText)findViewById(R.id.add_edt2);
        number = (EditText)findViewById(R.id.add_edt3);
        qq = (EditText) findViewById(R.id.add_edt4);
        email = (EditText)findViewById(R.id.add_edt5);
        address = (EditText)findViewById(R.id.add_edt6);
    }

    // get Input text
    private Contact getContent(){
        Contact contact = new Contact();
        contact.setName(name.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setNumber(number.getText().toString());
        contact.setQq(qq.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setAddress(address.getText().toString());
        return contact;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//载入界面菜单控件
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_save) {  // 保存
            if(name.getText().toString().equals(""))
                Toast.makeText(this, "姓名不能为空", Toast.LENGTH_LONG).show();
            else if(phone.getText().toString().equals(""))
                Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
            else {
                boolean flag = service.save(getContent());
                if(flag)
                    Toast.makeText(this, "联系人添加成功", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "联系人添加失败", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (id == android.R.id.home)  // 返回
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
