# **JLib  For AndroidX**

[ ![Download](https://api.bintray.com/packages/jewelbao88/ComponentsMaven/JLibX/images/download.svg) ](https://bintray.com/jewelbao88/ComponentsMaven/JLibX/_latestVersion)
## 介绍
 androidX、java工具类集合

 [无androidX依赖的工具类请移步到][14]

----------




## 在 build.gradle 中添加依赖
```
implementation 'com.jewel.libx:JLibX:1.0.1'
```

----------


## AndroidX ##

 - [CompatUtil][1]--版本兼容类,处理不同版本差异方法
 - [PermissionUtil][2]--权限工具，6.0后的权限请求封装
 - [SnackbarUtil][3]--Snackbar工具，封装几种常用提示Snackbar
 - [TextViewUtil][4]--TextView相关操作封装
 - [RecyclerViewUtil][5]--RecyclerView线性和网格列表的封装
 - [HandlerUtil][10]--简易的Handler UI线程调用工具
 - [SharedPre][11]--随存随取的SharedPre工具类，使用此工具需要在Application中先初始化
 - [live][12]--android lifecycle数据封装集合，简化接口类的数量，可与http请求的回调处理完美结合
 - [ScreenAdapter][13]--屏幕适配，各类尺寸转换

 - 陆续收录中...

----------


## Java ##

 - [DateUtil][6]--时间转换工具类,String-Long-Date互转
 - [DigitUtil][7]--数字处理工具类,四舍五入，比例计算等等
 - [StringUtil][8]--字符串格式化工具类，支持字符串资源文件
 - [FileUtil][9]--文件操作工具，包含创建文件/目录、(强制)删除、重命名
 - 陆续收录中...

----------


## License

```
Copyright 2018 jewelbao

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


  [1]: https://github.com/jewelbao/JLibX/blob/master/libx/src/main/java/com/jewel/libx/android/CompatUtil.java
  [2]: https://github.com/jewelbao/JLibX/blob/master/libx/src/main/java/com/jewel/libx/android/PermissionUtil.java
  [3]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/android/SnackbarUtil.java
  [4]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/android/TextViewUtil.java
  [5]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/android/recyclerView/RecyclerViewUtil.java
  [6]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/java/DateUtilser/JLib/src/main/java/com/jewel/lib/android/recyclerView/RecyclerViewUtil.java
  [7]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/java/DigitUtil.java
  [8]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/java/StringUtil.java
  [9]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/java/FileUtil.java
  [10]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/android/HandlerUtil.java
  [11]: https://github.com/jewelbao/JLibX/tree/master/libx/src/main/java/com/jewel/libx/android/SharedPre.java
  [12]: https://github.com/jewelbao/JLibX/blob/master/libx/src/main/java/com/jewel/libx/android/live
  [13]: https://github.com/jewelbao/JLibX/blob/master/libx/src/main/java/com/jewel/libx/android/ScreenAdapter.java
  [14]: https://github.com/jewelbao/JLib