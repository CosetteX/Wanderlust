# Wanderlust
Wanderlust 2020 Winter

2020.12.29（xk）

进度：

- 解决了日历中重复点击某个日期会产生闪退的bug
- 添加了新增日程的功能，新的item定义参考Bar_2_Item_new文件
- 写的时候感觉原来的数据库考虑起来思路不是很清楚……（太菜了 55）所以我又重新写了一个比较简单的表，把所有的信息都包在一张表里了，参考dbmanager目录；新建的表依然放在wanderlust_db.db的数据库文件中
- 初步解决bar3的显示



待完善：

- 目前添加日程的时候必须加入图片，需要修改掉这个限制
- 目前添加日程的时候还不能自动定位当前位置
- 目前添加的日程不支持编辑和删除功能
- “交通”类型事件需要能设置两个地点（！所以塞进同一张表并不合理 55）
- 待办事项设置：
  - 目前的待办判定：大于等于当前日期的就是待办，对所有的日程都进行待办判定
  - 需要修改的：只对待办事件设置待办判定，待办事项前面加上小框（这似乎又涉及数据库设计了（?））
- 数据库整合（大工程…）
- 私密事件设置
- ui美化
- bar3显示设置：
  - 目前的bar3显示所有的日程，而且是按照日程添加顺序显示的，需要修改
  - 目前，无日程时bar3显示为空，需要优化

备注：

- 参考了比较多网上的项目和他们的命名方式，为了方便有一些组件是别人那里抠下来的，我也还要研究研究……
- 基本上现在bar2和bar3用到的大部分东西都塞到新的一些目录下面了，回头我再把多余的代码删一删

2020.12.29 commit2

- 修改date的string类型为long，对日程排序；
- 取消了添加日程时必须加图片的限制；（添加了定位功能，但是在模拟器里有时候会出bug，定位不了，在手机上就不会，之前也是这样，不知道为什么）

2020.12.29 night commit
- 基于2020.12.29下午commit的版本，整合了臧和xk的数据库

2020.12.30 xk commit in the morning
将12.29晚上commit的版本整合到了有新的数据库的版本
