package com.example.a19454.biyesheji;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private EditText name=null;    // declare EditText
    private EditText phone=null;
    private EditText number=null;
    private EditText qq=null;
    private EditText email=null;
    private EditText address=null;
    private Button call=null;
    private Button sms=null;

    private Contact contact=null;
    private Service service=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        contact = new Contact();
        init();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            finish();
        }else{
            service = new Service(this);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//载入界面菜单控件
        getMenuInflater().inflate(R.menu.menu_det, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.det_mod) {  // 修改
            Intent intent = new Intent(DetailActivity.this, ModifyActivity.class);
            intent.putExtra("id", contact.getId());
            startActivity(intent);
        }
        if (id==R.id.det_delete)
        {
            dialog();
            return true;
        }

        if (id == android.R.id.home)  // 返回
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // init - get views by Id
    private void init(){
        name = (EditText)findViewById(R.id.det_tv1);
        phone = (EditText)findViewById(R.id.det_tv2);
        number = (EditText)findViewById(R.id.det_tv3);
        qq = (EditText) findViewById(R.id.det_tv4);
        email = (EditText)findViewById(R.id.det_tv5);
        address = (EditText)findViewById(R.id.det_tv6);
        call = (Button)findViewById(R.id.call);
        call.setOnClickListener(new ButtonCallListener());
        sms = (Button)findViewById(R.id.sms);
        sms.setOnClickListener(new ButtonSmsListener());
    }

    // create hint dialog
    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setMessage("确定删除吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                service.delete(contact.getId());
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onRestart() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        if(id == -1){
            finish();
        }else{
            service = new Service(this);
            contact = service.getById(id);

            name.setText(contact.getName());
            phone.setText(contact.getPhone());
            number.setText(contact.getNumber());
            qq.setText(contact.getQq());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
        }
        super.onRestart();
    }

    //**************** internal classes as Listener ********************
    class ButtonCallListener implements android.view.View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+contact.getPhone()));
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        }
    }
    class ButtonSmsListener implements android.view.View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:"+contact.getPhone()));
            intent.addCategory("android.intent.category.DEFAULT");
            startActivity(intent);
        }
    }
}
