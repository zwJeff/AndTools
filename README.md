# 说明： AndTools快速开发框架使用说明

 * 作者： 张武
 * 日期： 2017/6/5.
 * email: jeff_zw@qq.com

##   框架包含组件及工具类（持续增加中...）

### 模板类
* [进行ButterKnife綁定过的BasicActivity，并托管到AndTools的ActivityManager  AndToolsBasicActivity]()
* [进行ButterKnife綁定过的BasicFragment  AndToolsBasicFragment]()

### 自定义控件
* [图片轮播组件 BannerCircleView](https://github.com/zwJeff/AndTools/wiki/%E5%9B%BE%E7%89%87%E8%BD%AE%E6%92%AD%E7%BB%84%E4%BB%B6%E8%AF%B4%E6%98%8E)
* [通用标题栏组件 TitleView](https://github.com/zwJeff/AndTools/wiki/%E9%80%9A%E7%94%A8%E6%A0%87%E9%A2%98%E6%A0%8F%E7%BB%84%E4%BB%B6-TitleView)
* [类似头条 可滚动的标题选择栏 TabStrip]()
* [不可滚动的GridView NoScrollGridView]()
* [横向滚动的ListView HorizontalListView]()
* [横向铺开的可自动换行的RadioGroup MultiLineRadioGroup]()

### 工具类
* [对XUtils网络工具封装的 XNetUtils]()
* [对XUtils图片加载工具封装的 XImageLoaderUtils]()
* [位图工具类 BitmapUtils]()
* [网络检测工具类 NetCheckUtils]()
* [字符串工具类 StringUtils]()
* [UI工具类 UIUtils]()


###   Android Studio Gradle 快速引用
####  1.配置JitPack仓库
``` gradle
allprojects {
            repositories {
                jcenter()                     //安卓默认的jcenter库
                maven { url 'https://jitpack.io' } //为使用AndTools新增的库
            }
        }
```
####  2.在 dependencies 中 引入AndTools库 
``` gradle
dependencies {
	compile 'com.github.zwJeff:AndTools:1.0.11'
}
```

###   各个组件工具类 的快速使用

#### 使用方法 参见各个组件和工具类说明
