# 说明： 图片轮播主类

 * 作者： 张武
 * 日期： 2016/9/2.
 * email: jeff_zw@qq.com

##          实现原理：
* 通过将adapter的count设置为极大值，实现同方向无穷轮播
* 通过Timer，在每一页图片显示的时候，设置一个延时跳到下一页的操作
* 通过ImageViewLoader加载图片

##          优点：
* 最后一张图片到第一张图片过渡自然
* 每一张图片都会显示指定延迟时间，不会因为人为滑动导致的跳页和时间错乱
* 通过ImageViewLoader加载图片可实现图片缓存，不会出现过多图片造成oor
* 通过传入url的数量动态决定轮播图的数量
 
 

**用例：**

 1）布局文件：

    <com.jeff.mybannercircledemo.BannerCirlePlayer
       android:id="@+id/banner_circle_piayer"
       android:layout_width="match_parent"
       android:layout_height="150dp">
    </com.jeff.mybannercircledemo.BannerCirlePlayer>

 2）初始化：

        play = (BannerCirlePlayer) findViewById(R.id.banner_circle_piayer);
        final List<String> urlList = new ArrayList<>();
        urlList.add("url.jpg");
        urlList.add("url.jpg");
        List<View.OnClickListener> lis = new ArrayList<>();
        lis.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //点击事件
            }
        });
        lis.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //点击事件
            }
        });
        play.setmImageLoader(new MyImageLoader(MainActivity.this));
        play.setData(urlList,lis,true);
