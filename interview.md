# create by ximano 2018年11月1日10:20:33
### 1. Intent传递数据时，下列哪些数据类型可以被传递？[pic-1]
`A Serializable` &emsp;&emsp; `B CharSequence` &emsp;&emsp; `C Parcelable` &emsp;&emsp; `D Bundle`

- ANSWER：*`ABCD`*  &emsp; [参考链接](https://blog.csdn.net/a136447572/article/details/82288989)
- COMMON USAGE

> 传递Object(对象须实现Serializable, 示例demoObject)

    mIntent.putExtra("demoObject", demoObject);// 第一种设置数据

    Bundle bundle = new Bundle();// 第二种设置数据
    bundle.putSerializable("demoObject", demoObject);
    mIntent.putExtras(bundle);

    DemoObject demoObject = (DemoObject) getIntent().getSerializableExtra("demoObject");// 获取数据

> 传递List(List中的对象须实现Serializable, 示例demoList)

    mIntent.putExtra("demoList", (Serializable) demoList);// 设置数据

    DemoList demoList = (List<Demo>) intent.getSerializableExtra("demoList");// 获取数据

### 2. 下面退出Activity错误的方法是？[pic-1]
`A finish()` &emsp;&emsp; `B 抛异常强制退出` &emsp;&emsp; `C System.exit()` &emsp;&emsp; `D onStop()`

- ANSWER：*`ABCD`*  &emsp; [参考链接](https://blog.csdn.net/a136447572/article/details/82288989)


> Actiivty什么时候会异常退出？
> [Android界面横竖屏切换](https://blog.csdn.net/man_embedded/article/details/52625619)
> Actiivty异常退出又重新创建什么时候出现？
>  Actiivty异常退出数据是如何保存的？又如何取出重新绘制视图？Android的委托思想
> IPC和aidl
> [对象的序列化和反序列化](https://blog.csdn.net/jiuchen4107/article/details/52381744)