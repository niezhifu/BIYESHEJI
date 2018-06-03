package com.example.a19454.biyesheji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Objects;

public class ModifyActivity extends AppCompatActivity {
    private EditText name=null;    // declare EditText
    private EditText phone=null;
    private EditText number=null;
    private EditText qq=null;
    private EditText email=null;
    private EditText address=null;

    private Service service=null;
    private Contact contact=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        contact = new Contact();
        service = new Service(this);
        init();  //init
        // show the detail of the contact
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            finish();
        }else{
            contact = service.getById(id);

            name.setText(contact.getName());
            phone.setText(contact.getPhone());
            number.setText(contact.getNumber());
            qq.setText(contact.getQq());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
        }

        // 标题栏添加“返回”菜单
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    // init - get views by Id
    private void init(){
        name = (EditText)findViewById(R.id.mod_edt1);
        phone = (EditText)findViewById(R.id.mod_edt2);
        number = (EditText)findViewById(R.id.mod_edt3);
        qq = (EditText) findViewById(R.id.mod_edt4);
        email = (EditText)findViewById(R.id.mod_edt5);
        address = (EditText)findViewById(R.id.mod_edt6);
    }
    // get Input text
    private Contact getContent(){
        Contact c = new Contact();
        c.setId(contact.getId());
        c.setName(name.getText().toString());
        c.setPhone(phone.getText().toString());
        c.setNumber(number.getText().toString());
        c.setQq(qq.getText().toString());
        c.setEmail(email.getText().toString());
        c.setAddress(address.getText().toString());
        return c;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//载入界面菜单控件
        getMenuInflater().inflate(R.menu.menu_mod, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mod_save) {  // 保存
            if (name.getText().toString().equals(""))
                Toast.makeText(this, "姓名不能为空", Toast.LENGTH_LONG).show();
            else if (phone.getText().toString().equals(""))
                Toast.makeText(this, "手机不能为空", Toast.LENGTH_LONG).show();
            else {
                boolean flag = service.update(getContent());
                if (flag)
                    Toast.makeText(ModifyActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ModifyActivity.this, "修改失败", Toast.LENGTH_LONG).show();
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
