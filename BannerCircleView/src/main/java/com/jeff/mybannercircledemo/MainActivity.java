package com.jeff.mybannercircledemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BannerCirlePlayer play;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (BannerCirlePlayer) findViewById(R.id.banner_circle_piayer);
        final List<String> urlList = new ArrayList<>();
        urlList.add("http://img.mukewang.com/5704ae850001f59906000338-240-135.jpg");
        urlList.add("http://img.mukewang.com/540e57300001d6d906000338-240-135.jpg");
        urlList.add("http://img.mukewang.com/5704ae850001f59906000338-240-135.jpg");
        urlList.add("http://img.mukewang.com/540e57300001d6d906000338-240-135.jpg");
        List<View.OnClickListener> lis = new ArrayList<>();
        lis.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("www.baidu.com");   //指定网址
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);           //指定Action
                intent.setData(uri);                            //设置Uri
                MainActivity.this.startActivity(intent);        //启动Activity

            }
        });
        lis.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("www.baidu.com");   //指定网址
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);           //指定Action
                intent.setData(uri);                            //设置Uri
                MainActivity.this.startActivity(intent);        //启动Activity
            }
        });
        lis.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("www.baidu.com");   //指定网址
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);           //指定Action
                intent.setData(uri);                            //设置Uri
                MainActivity.this.startActivity(intent);        //启动Activity
            }
        });
        lis.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("www.baidu.com");   //指定网址
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);           //指定Action
                intent.setData(uri);                            //设置Uri
                MainActivity.this.startActivity(intent);        //启动Activity
            }
        });



    }
}
