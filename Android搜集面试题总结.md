# ----搜集面试题总结-----
#捷通华声面试#
 - 1.基本数据类型各占几个字节：
 基本数据类型：
	整数类型：
		byte: 1个字节
		short: 2个字节
		int: 4个字节
		long: 8个字节
	字符类型：
		char: 2个字节
	浮点类型：
		float: 4个字节
		double: 8个字节
	布尔类型：
		boolean: 1位
 - 2.String s1 = "Hello World";
 	 String s2 = "Hello World";
   	 System.out.println(s1==s2);结果是什么为什么？
	但是在Java中，如果将同一个字符串常量赋给多个字符串变量来创建字符串对象的时候，则这些字符串对象具有相同的内存地址，因为同一个字符串常量在内存中的地址是不变的，所以多个对象共用一个内存地址。所以打印结果是true。
 - 3.写两个类，一个构建二叉树，一个找出二叉树任意一个节点所有叶子节点的个数
 - 4.Android中Aidl和JNI分别是什么，有什么作用？
	- IPC，aidl机制
    简单的说：Adil实现了进程间通信，JNI解决了Java和C/C++的互通
	IPC inter-process commucation:进程间通信(远程服务的调用)，也就是aidl机制

	本地服务的调用：bindService()服务的时候，会传递一个连接桥，ServiceConnect(是接口类型，需要在Activity中创建这个借口的实例，重写两个方法onServiceConnected()..),此时服务会得到这个连接桥，并在onBind()方法中，会把IBinder类型的变量返回给onServiceConnected()这个方法，因为我们知道IBinder是接口类型，我们在这个service中创建个实现IBinder类型的内部类，在这个内部类中创建方法，来调用Service中的方法。这样我们就可以在Activity中ServiceConnect实现类的onServiceConnected()方法中，对IBinder进行强转，得到一个实例，从而在Activity中间接调用Service中的方法。
	
	远程服务的调用：
	基于本地服务的调用，从一个程序中的Activity调用另一个程序的Service中的方法，其实思路是和本地调用服务中方法是一样的，只不过遇到一个问题，在一个程序中无法得到另一个程序中一个对象的类型，所以对返回的IBinder类型无法进行强转。这样就进入了进程间通信的aidl机制了。
	创建一个接口，定义方法。将此.java文件，的后缀改为.aidl,提示语法错误的时候，将修饰符去掉，于是在工程目录的gen目录下系统会生成同名的Java文件。	
	并且public static abstract class Stub extends android.os.Binder implements com.itheima40.alipayservice.AlipayRemoteService

	这样我们在Service中创建一个内部类，继承Stub,那么也就相当于继承可Binder和AlipayRemoteService，当然需要实现AlipayRemoteService的方法。在这个方法中可以任意调用Service中的方法。
	同时.aidl文件复制到其他程序中，切记包名必须一致，于是在工程目录的gen目录下系统会生成同名的Java文件。这样同名的aidl文件把两个程序进行关联。
	在ServiceConnected实现类的onServiceConnected()方法中，使用Stub.asInterface(service);返回给一个AlipayRemoteService接口类型的变量，实现间接的调用Service中的方法。
	-JNI
	NDK工具，和相关配置，以及JNI的相关命令，在Android代码中创建public native String getString();的方法。
		

 - 5.Service两种启动模式，及生命周期各是怎样？
	- 服务生命周期

	- 开启方式, 特点: 把服务启动起来后, 就不管服务的事了.. activity和服务没有关系.
		- startService, 生命周期执行的过程: onCreate -> onStart/onStartCommand 服务正在运行中
		- stopService, 生命周期执行的过程: onDestory 服务销毁了
		- 使用startService方式开启的服务, 只能用stopService关闭服务.

	- 绑定方式, 特点: 不求同年同日生, 但求同年同日死.
		- bindService, 生命周期执行的过程: onCreate -> onBind 
		- unbindService, 生命周期执行的过程: onUnbind -> onDestory

	- onRebind 方法, 只有在onUnbind方法返回true的情况下, 才会被调用.



	1.onCreate()--onStart()---onDestroy()
	2.onCreate()--onBind()---onUnbind()--onDestroy()
 - 6.兽鸟大战期间，蝙蝠见兽实力大跟兽说我跟你们一样有牙齿吃肉我是兽，后来鸟族势力大了，蝙蝠有对鸟说，我跟你们一样有翅膀我会飞我是鸟，根据以上描述，写一个借口三个类，描述蝙蝠在兽鸟大战中的位置？

 #不知道#
 - 1.单线程模型下Handler，Message，MessageQueue，Looper的关系？
	Handler实现线程间通信的原理，
	子线程中的Handler发送Message到主线程的消息队列中，主线程中程序在启动的时候main()方法中执行一个prepareMainLooper()方法，此方法中又执行一个prepare()方法，该方法内部new Looper()该构造中又new一个MessageQueue()实例，Looper.loop()方法中维护一个while(true)的死循环，Message.next()方法，就是在消息队列中不停的取有没有下一个消息，如果有下一个消息，主线程中的Handler实例执行一个HandleMessage()方法，对消息进行处理 
 - 2.ContentProvider如何实现数据共享？
	ContentProvider是类似数据库表的方式将一个程序的数据暴露出去，我们程序员就通过ContentResolver这个类对暴露出来的数据库表进行操作。
 - 3.在工作过程中如何进行UI匹配？
	屏幕适配方式：
	1.图片适配
	2.dimens.xml文件适配
	3.布局文件适配
	4.权重适配 
	5.Java代码适配
 - 1.图片适配：切图的时候，两套图：800*480，1280*720。一套：800*480，因为导航图是全屏图，在不同分辨率的手机上变形效果严重，所以需要单独截取。
	- drawable-ldpi--->320*240分辨率,此分辨率的手机去找这个文件夹下的图片
	- drawable-mdpi--->480*320分辨率,..   图片尺寸：48x48px
	- drawable-hdpi--->800*480分辨率,..   图片尺寸：72x72px
	- drawable-xhdpi--->1280*720分辨率,..  图片尺寸：96x96px
	- drawable-xxhdpi--->1920*1080分辨率,..图片尺寸：144x144px
	- 当前分辨率下没有相应图片的时候，去加载高分辨率的图。
 - 2.dimens.xml文件适配（没有dip的适配方式,如果真的达到了是适配效果，只是一种巧合,比如：320*240，480*320，800*480，在布局中设置160dp，那么刚好是屏幕的一半）
	- 手机分辨率：800*480(注：手机两个直角边上分别放置了800和480个像素点)
	- 手机尺寸大小：手机屏幕斜边的大小
	- 像素密度比：sqrt(800*800 + 480*480) / 3.7 ≈ 293.72
  	- 根据Google官方文档上找到5种像素密度等级，然后比对计算出的像素密度比和哪个接近，从而得到 dp = px的转换关系
		（320*240）ldpi：120dpi，像素密度与dp转换关系为：1dp = 0.75px   160dp * 0.75 = 120px = 1/2screenWidth
		（480*320）mdpi：160dpi	，像素密度与dp转换关系为：1dp = 1px    160dp * 1 = 160px = 1/2screenWidth
		（800*480）hdpi：240dpi，像素密度与dp转换关系为：1dp = 1.5px    160dp * 1.5 = 240px = 1/2screenWidth
		（1280*720）xhdpi：320dpi，像素密度与dp转换关系为：1dp = 2px    180dp * 2 = 360px = 1/2screenWidth
		（1920*1080）xxhdpi：480dpi，像素密度与dp转换关系为：1dp = 3px  180dp * 3 = 540px = 1/2screenWidth
	- 使用方式：values-1280x720--->dimens.xml：<dimen name="width">180dp</dimen>
			   android:layout_width="@dimen/width"
			   同时需要在values文件夹下的dimens文件中加上<dimen name="width">160dp</dimen>来适配前三种分辨率的手机
			   如果需要适配1290*1080分辨率的手机，还有继续创建新的values-文件夹
 - 3.布局文件适配
	- layout-1280x720，layout-800x480不同屏幕分辨率的手机自动去寻找不同的文件夹，然后使用不同的布局
 - 4.Java代码适配
	
 - 5.权重适配
	- 就是使用Linearlayout布局方式，剩余空间的分配，达到适配的效果

 - 6.动态适配

 - 4.注册广播有几种方式，用关键代码简述？
 	在AndroidManifest.xml中注册广播者
    <receiver android:name="com.itheima.mobilesafetest.recevier.BootCompletedReceiver" >
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
	</receiver>

	在代码中注册广播接受者
	private class SMSRecevier extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
		
		}
	}
	SMSRecevier receiver = new SMSRecevier();
	IntentFilter filter = new IntentFilter();
	filter.addAction("android.provider.Telephony.SMS_RECEIVED");
	registerReceiver(receiver, filter);
	
 - 5.横竖屏切换时Activity的生命周期？
 	默认情况下，横竖屏时：
	执行：onPause()--onStop()---onDestroy()---onCreate()--onStart()--onResume()
	当在： 在Activity中配置：android:configChanges="orientation|keyboardHidden|screenSize"
	横竖屏时只执行：onConfigurationChanged()方法
 - 6.解析Json用到的工具，有什么区别？
	gson和fastjson我都在项目使用过了，相比较而言，gson 对字段的处理更细腻一些，有版本号的概念，相对更灵活，值得注意是，gson直接入侵字段，则不是set get 方法；fastjson 速度更快，但字段的处理不够灵活，特别是处理同一个Javabean的时候，字段没有版本概念，因此有时候要手动处理。
	但同时，两个组件都对原程序有着不同程度的入侵，gson 使用 Expose、Until 等注解，fastjson 使用的是 JSONField、JSONCreator等注解
 	
 - 
 - 7.开发过程中用过的Github上相关的框架,它们的作用和功能？
 	SMSSDK:第三方登录和分享
	AsyncHttp：网络请求
	pulltorefresh：下拉刷新上拉加载
	UniversalImageLoader：多线程下载图片，图片的缓存
	xUtils：ViewUtils,HttpUtils,BitmapUtils
 #不知道#
 - 1.大图片不能压缩，怎么加载进来？(如何加载大图片？)
 	每一个像素占用的空间：总像素个数 * 像素的单位(ARGB_4444--2byte,ARGB_8888--4byte,RGB_565---2byte)一种颜色格式
	宽度像素数 * 像素单位 / 手机屏幕宽 = A
	高度像素数 * 像素单位 / 手机屏幕高 = B

	那个倍数比较大使用那个数进行缩放
		String path = etPath.getText().toString();
		
		Options opts = new Options();
		opts.inJustDecodeBounds = true; // 设置为true, 加载器不会返回图片, 而是把Options对象中以out开头的字段给设置了.
		BitmapFactory.decodeFile(path, opts);//这句有用吗？
		
		// 得到了图片的宽和高
		int imageWidth = opts.outWidth;
		int imageHeight = opts.outHeight;
		System.out.println("图片的宽和高: " + imageWidth + " * " + imageHeight);
		
		// 获取屏幕的宽和高
		Display display = this.getWindowManager().getDefaultDisplay(); // 获取默认窗体显示的对象
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		System.out.println("屏幕的宽和高: " + screenWidth + " * " + screenHeight);
		
		// 计算缩放比例
		int widthScale = imageWidth / screenWidth;
		int heightScale = imageHeight / screenHeight;
		
		int scale = widthScale > heightScale ? widthScale:heightScale;
		System.out.println("缩放比例: " + scale);
		
		// 使用计算出来的比例进行缩放
		opts.inJustDecodeBounds = false; // 指定加载可以加载出图片.
		opts.inSampleSize = scale;
		Bitmap bm = BitmapFactory.decodeFile(path, opts);

		// 显示到ImageView控件上
		ivIcon.setImageBitmap(bm);
	
	

 - 2.如何看待Android的内存，怎么处理内存溢出？
 	- RAM：RandomAccessMemory--易挥发性随机存取存储器
	可以理解为运行内存，在Android操作系统中，RAM的大小直接决定了你在手机后台能开多少进程。

	- ROM：ReadOnyMemory--只读存储器。包含系统空间+用户安装程序空间+用户存储空间

	系统空间：是整个手机存储的核心，视具体手机定制厂商添加了多少APK(也就是系统软件)而决定占用的空间大小，用户在屋root的情况下无法修改它。

	用户安装程序空间：比如下载一个QQ安装包为24.1MB，安装后占用空间是39MB，用户安装程序空间。

	用户存储空间：用于我们存放歌曲，电影，照片。

	即便有时候我们额外加了一张内存卡，大小足够安装一些大型游戏，但是安装的时候还是提示内存不足，因为无论是大游戏还是小游戏，都需要数据包的支撑，只不过一些小游戏的数据包较小，直接集成在游戏主程序中了，但是大游戏的数据包都在默认存储的指定路径。也就是说如果把数据包安装到外部存储中，游戏本身是读取不到的，所以不能正常运行。

	ROM与电脑的硬盘类似，它并不影响手机的运行速度，但是如果硬盘中装了过多的软件，占用了很大的存储空间，读取信息时候回耗费很多资源，从而将影响手机的运行速度。

	1.最常见的内存溢出，加载大图片...
	安卓的虚拟机是基于寄存器的Dalvik，它的最大堆大小一般是16M。
	2.尽量在Activity的生命周期结束时，在onDestroy中把我们做引用的其他对象做释放，比如：cursor.close()。
	3.getApplicationContext()的生命周期要比Context的要长
	4.注意Context引用的持有，防止内存泄漏。
	5.自定义我们的应用需要多大的内存，这个好暴力哇，强行设置最小内存大小：VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE);
	
 - 3.程序闪退，如何处理？
 	当然首先要找到闪退的原理，看log,做具体的处理操作。

 - 4.数据的接口打包成API好还是SDK好，(不确定是不是SDK，反正是另一种方式)为什么？
	API:也就是 Application Programming Interface，其实就是操作系统留给应用程序的一个调用接口，应用程序通过调用操作系统的 API 而使操作系统去执行应用程序的命令（动作）。

	SDK:辅助开发某一类软件的相关文档、范例和工具的集合都可以叫做“SDK”。

	SDK是一系列文件的组合，包括lib、dll、.h、文档、示例等等；API是对程序而言的，提供用户编程时的接口，即一系列模块化的类和函数。可以认为API是包含在SDK中的。
 - 5.给我一个项目，7个人1个月时间开发，你需要多长时间熟悉，从哪方面下手？
 - 6.要熟悉一个项目，我们给你提供什么东西？
 - 7.有没有转产品的想法？
 - 8.怎么看待时间节点？(意思是项目的技术要求时间和老板要求时间不一致，怎么处理，按期交任务怎么对待？)
 - 9.怎么看待加班？
 - 10.对公司有什么其他的问题？
 - 11.期待薪资，底限是多少？


 #不知道#
 - 1.二年开发经验，代码写多少行合适？
    编程经验，也有人用编码量来衡量(尤其在招聘程序员是经常会用到)，这有其合理性也有局限性。它只是一个参考而已。一般的程序员，每年的编码量大约为2、3万行左右（指的是软件产品开发）。十万行的编程经验大概是3年以上的实际工作经验。所以两年开发经验，也就是6W~8W的代码量。
 - 2.登录界面,就是给你个用户名,密码,登录按钮,然后你说,想到什么说什么？
 - 3.Handler机制，属于进程间通信还是线程间通信，管道和socket你怎么理解？
 - 4.ListView如何优化？
		1.使用历史缓存的View，convertView,为避免每显示一个Item，都会创建一个View对象，我们使用convertView,这样一来我们只需要创建一个屏幕的View对象，上拉显示的新的View,就使用历史缓存的convertView,
		2.在Adapter中getView()方法中，我们知道每加载一个Item，都要从布局文件中findViewById()非常浪费资源，使用ViewHolder(),保存控件在布局文件中的层次结构
		3.Adapter对象的复用，在ListView分批加载的时候，每获取一屏幕的item的时候，就会给Handler发送消息，然后listview.setAdapeter(new XXXAdapter());一方面会浪费一点资源，
		另一方便，上拉加载的时候，用户体验不是很好。

		list_callsms.setSelection(index);不推荐使用，
 - 5.ANR怎么避免？
 	
 - 6.屏幕适配问的比较多？
 - 
 - 7.gone属性，是占的位置被释放，InVisible不释放位置，不适用哪个标题，就把占有的空间释放掉？
	gone:隐藏控件，但是控件占用的位置没有释放掉，使用的时候注意在隐藏和显示的时候是否会影响其他布局
	inVisible:不但隐藏，位置也释放掉


 #不知道#
 - 1.NDK是什么？
 	 NDK提供了一系列的工具，给开发者提供了C的动态库，能够自动的将so和java文件一起打包成apk,
	 NDK提供 的相应的mk文件隔离CPU,平台，和API，创建so库。
 - 2.系统上安装了多种浏览器，能否指定某个浏览器访问指定页面？请说明理由
 - 3.Android中有哪几种Json解析方式？
 	SAX：简单的说就是对文档进行顺序扫描，扫描到文档开始和结束或者元素开始和结束等地方的时候，通知事件处理函数，由事件处理函数做相应的动作，然后做继续的扫描，直到文档结束。它的特点是不用 事先调入整个文档，占用资源少。
	【对文档进行顺序扫描，扫描到文档(document)开始或者结束的地方，或者元元素(element)开始或者结束的时候，调用事件处理，处理函数做相应动作，然后继续扫描，直到文档结束】

	dom:DOM即一个文档对象模型，将整个XML文档载入内存，通过树形结构存储xml文件，将每个节点当做一个对象。效率低不推荐使用。
	
	Pull:pull解析和SAX解析类似，也提供了类似的事件，但是触发相应的事件之后，调用方法返回的事int型的整数，并且pull解析可以在程序中控制，想解析到哪里就停在哪里，Android中当然推荐pull解析。



 - 4.如何理解Android中的IPC？
	aidl
 - 5.请简述Android中如何电泳C/C++库？
 - 6.请简述自己在以往项目中解决过的难题？
 - 7.Android中有哪几种解析xml的类？官方推荐那种？以及他们的原理有什么区别？
 - 8.请介绍Android中的数据存储方式？
 - 9.请提供内容提供者是如何实现数据共享的？
	内容提供者，我们可以把它看做是提供对本程序内数据增删改查的一个类，insert()，delete(),update(),我们可以通过内容解析者，通过一个Uri的匹配，调用对应的方法，进而达到对另一个程序内的数据进行操作。
	
 - 10.如何退出Activity，如何安全退出已调用多个Activity的Application？
	1、抛异常强制退出：
	该方法通过抛异常，使程序Force Close。
	验证可以，但是，需要解决的问题是，如何使程序结束掉，而不弹出Force Close的窗口。
	2、记录打开的Activity：
	每打开一个Activity，就记录下来。在需要退出时，关闭每一个Activity即可。
	public class CloseActivityClass { 

	public static List<Activity> activityList = new ArrayList<Activity>();

		public static void exitClient(Context context) {
		
			for (int i = 0; i < activityList.size(); i++) {
				if (null != activityList.get(i)) {
					// 循环把所有的Activity都finish掉 
					activityList.get(i).finish();			
				
				}						
			}	
		}

	}

	在所有Activity的基类的onCreate()方法中添加：CloseActivityClass.activityList.add(this);
						退出时，只需调用：CloseActivityClass.exitClient(MainActivity.this);即可
	3、发送特定广播：
	在需要结束应用时，发送一个特定的广播，每个Activity收到广播后，关闭即可。
	4、递归退出
	在打开新的Activity时使用startActivityForResult，然后自己加标志，在onActivityResult中处理，递归关闭。
 - 11.谈谈Android中的IPC(进程间通信)机制？
 - 12.Android中如何自定义控件？
 - 13.Android中多个Activity间如何实现数据共享？
	1.基于消息的通信机制  Intent ---boudle ,extra

	数据类型有限，比如遇到不可序列化的数据Bitmap,InputStream, 或者LinkList链表等等数据类型就不太好用。
	
	2.利用static静态数据，public static成员变量；
	
	3.基于外部存储的传输,  File/Preference/ Sqlite ，如果要针对第三方应用需要Content Provider
	
	4.基于IPC的通信机制context 与Service之间的传输，如Activity与Service之间的通信，定义AIDL接口文件。
	
	5.基于Application Context，因为一个应用只有一个Application，创建一个类继承Application,把一些数据保存在这个类中，
 - 14.Android中如何实现多线程？
 - 15.请简述用过哪些开源框架？
 - 16.请简述Android中数据库的使用？
	 - 创建类继承SQLiteOpenHelper，在openhelper实例化时候创建数据库，并实现两个方法：onCreate(数据库创建成功，回调这个方法，这里适合创建一些表)
	 - onUpgrade(数据库版本变化时候，回调这个方法，这里适合增加一些字段，更新数据库 )
	 - openhelper调用getReadableDatabase()，得到一个数据库db,db通过insert，update，query，update等方法对数据库进行增删改查。

 #不知道#
分享一下今天的面试
1. handler不加static为什么会有内存泄露
2. application的framework有什么,
3. 为什么不能在broadcastreceiver中开启子线程
	广播接收者的生命周期是非常短暂的，在接收到广播的时候创建，onReceive()方法结束之后销毁
	广播接收者中不要做一些耗时的工作，否则会弹出Application No Response错误对话框
	最好也不要在广播接收者中创建子线程做耗时的工作，因为广播接收者被销毁后进程就成为了空进程，很容易被系统杀掉
	耗时的较长的工作最好放在服务中完成。
4. jvm与dalvik的区别
5. jvm是怎样工作的
6. 当前的activity是singletop的,而且在栈的最顶端,如果我们从service中执行startAcitvity来开启这个activity,会执行哪些方法
	
7. 如果我要让手机每三天做一件事,你会怎么设计,有哪些需要注意的
	
8. 画一下你的app的模型图
9. 


 #不知道#
 - 1.内存泄露的问题？



 #拉手网
 - 1.怎么解决ListView和ScrollView滑动事件的冲突？

	
 - 2.Android中触摸事件的分发机制和处理机制？
 - 3.ListView中每个Item都必须是相同的View吗？
 - 4.Fragment和Activity有什么区别，分别生命周期是什么？

	Fragment的生命周期：
	onAttach()---onCreate()---onCreateView()---onActivityCreated()---onStart()---onResume()---onPause()---onStop()---onDestroy()---onDestroyView()--onDetach()
 - 5.Android中怎么播放GIF动画？帧动画和补间动画有什么区别？

	帧动画：FrameAnimation,将若干个图片添加到一个xml文件中，将这个xml文件作为一个图片设置到一个ImageView中去，再把图片取出来，播放动画
	补间动画：代码中播放，xml文件播放（渐变，伸缩，位移，旋转，组合动画）
 - 6.布局文件中include标签怎么使用？必须要给它设置layout_width和layout_height属性吗？为什么？
 - 7.如何对于TextView中各个部分的文本来设置字体，大小，颜色，样式，以及超链接等属性？
 - 8.从界面A跳转到B界面，再从B界面跳转到C界面，从C界面操作完成后返回A，如何间数据从C界面传递给A界面？
 - 9.如何界面按钮多次点击，打开多个重复页面？
 - 10.WebView加载有几种方式？如何做屏幕适配？
 - 11.如何处理多个开发请求的回调(对应关系)？
 - 12.界面发起多个请求获取数据，由于服务器响应时间不同，填充数据时会有闪烁的情况，如何解决？
 - 13.由于多次快速点击按钮连续发送同样的请求数据到接口这类问题，如何更好方便的解决？
 - 14.代码编写一个简单静态工厂模式的例子？(抽象工厂模式，单例模式，观察者模式)

 #北京北信源软件股份有限公司 面试
 - 1.四大组件：Activity，Service服务，ContentProvider内容提供者，BroadcastReceiver广播接收者
 - 2.进程被Kill掉的优先级(5个)：
	1).前台进程
	2).可见进程
	3).服务进程
	4).背景进程
	5).空进程 

 - 3.非UI线程调用UI线程的API：

 - 4.Android中的五大布局：LinearLayout，RelativeLayout，AbsoluteLayout，FramLayout，TableLayout，GridLayout
 - 5.FrameLayout中的子元素总是以屏幕的左上角层叠在一起。
 - 6.Android系统提供的textColorSecondary怎么写？
	android:textColor="@android:color/black" 
 - 7.URI中的"#"和"*"分别表示什么含义：
 - 8.ScrollView最多可以放几个Layout？
 - 9.字节转字符串，byte b = 'abc'; String s = new String(b，"GB2312");
 - Service中START_NOT_STICKY和START_STICKY有什么区别？
	1、START_STICKY：当服务进程在运行时被杀死，系统将会把它置为started状态，但是不保存其传递的Intent对象，之后，系统会尝试重新创建服务; 
 
	2、START_NOT_STICKY：当服务进程在运行时被杀死，并且没有新的Intent对象传递过来的话，系统将会把它置为started状态，
 - 10.Android中访问需要权限的资源时，需要声明权限，使用什么标签？
	<uses-permission />

 - 11.什么是ANR，怎么避免？
	Application Not Responding:主线程在5秒内没有响应
	出现场景:
	1.在主线程中进行网络的访问，
	2.在主线程中进行数据库的增删改查的频繁操作。
	解决方式是，避免一些耗时的操作在主线程中操作，可以新开线程操作。然后使用Handler机制。
 - 12.Android中数据存储的5中方式？
	1 使用SharedPreferences存储数据
		轻量级的存储类，常用语保存状态，用于整形，布尔型，和字符串类型数据的保存。
	        SharedPreferences sp = context.getSharedPreferences("SP", MODE_PRIVATE);
	        //存入数据
	        Editor editor = sp.edit();
	        editor.putString("STRING_KEY", "string");
	        editor.putInt("INT_KEY", 0);
	        editor.putBoolean("BOOLEAN_KEY", true);
	        editor.commit();
        
	2 文件存储数据
		本质上就是通过流的形式存储数据到文件。
	3 SQLite数据库存储数据
		轻量级数据库，支持SQL语言，真用很少的内存却有很好的性能！
	4 使用ContentProvider存储数据
		Android中一个程序的数据是私有的，两个程序之间数据的交换就是使用ContentProvider，像联系人信息和图片库，都是以这种方式实现数据共享的。
	5 网络存储数据
		请求和发送实现的。
 - 13.一个APP中所有的Activity和Service是不是都跑在同一个进程中？
	 一般来说：同一个包内的activity和service，如果service没有设定属性android:process=":remote"的话，service会和activity跑在同一个进程中，由于一个进程只有一个UI线程，所以，service和acitivity就是在同一个线程里面的。android:process=":remote"值得注意他的用法！！！如果Activity想访问service中的对象或方法，如果service设定属性android:process=":remote"，那么就是跨进程访问
	
 - 14.自定义ListView背景之后，当list滑动时候会显示黑色背景，如何解决这种问题？
	
	
 - 15.Dialog中dissmissDialog()和removeDialog()区别是什么？
	（1）dismissDialog：隐藏，仅仅是不在界面显示
	（2）removeDialog：销毁对话框对象，释放其内存
 - 16.写出两个Button左右均匀，文字居中的样式?
 - 17.一个Button，按下的时候显示红色，有焦点的时候是蓝色，默认是灰色？
	<?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <item android:state_pressed="true"
              android:drawable="@drawable/function_red_pressed" /> 

        <item android:state_focused="true"
              android:drawable="@drawable/function_blue_pressed" /> 

        <item android:drawable="@drawable/function_gray_normal" /> 
    </selector>
	
 - 18.单例模式的例子？
	1).将构造函数私有化
	2).在类中创建一个本类对象
	3).定义一个共有方法，获取该对象
	// 饿汉式
	class Single {
		private Single() {}
		private static final Single s = new Single();
		public static Single getInstance() {
			return s;
		}
    }
	// 懒汉式
	class Single {
		private Single() {}
		private static Single s = null;
		public static Single getInstance() {
			if(s == null) {
				s = new Single();
			}
			return s;
		}
	}
	// 单例模式解决线程安全问题
	class Single {
		private Single() {}
		private static Single s = null;
		public static Single getInstance() {
			if(s == null) {
				synchronized(Single.class) {
					if(s == null) {
						s = new Single();
					}
				}
			}
			return s;
		}
	}
	观察者模式：
	定义对象间一种一对多的依赖关系，使得当每一个对象改变状态，则所有依赖于它的对象都会得到通知并自动更新。
	观察者模式定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态上发生变化时，会通知所有观察者对象，使它们能够自动更新自己。

	一个类继承Observable作为被观察者， 实现setChanged();notifyObservers();方法，另一个类实现Observer，作为观察者，实现其update()方法，使用的时候我们将 observable.addObserver(myObserver);观察者添加到被观察者中去，当被观察者做相应改变的时候，notifyObservers()，提示观察者中的update()执行相应操作。
	工厂模式：
	在工厂方法模式中，核心的工厂类不再负责所有的对象的创建，而是将具体创建的工作交给子类去做。这个核心类则摇身一变，成为了一个抽象工厂角色，仅负责给出具体工厂子类必须实现的接口，而不接触哪一个类应当被实例化这种细节。
	
	在以下情况下，适用于抽象工厂模式：
	(1) 当一个类不知道它所必须创建的对象的类的时候。
	(2) 当一个类希望由它的子类来指定它所创建的对象的时候。
	(3) 当类将创建对象的职责委托给多个帮助子类中的某一个，并且你希望将哪一个帮助子类是代理者这一信息局部化的时候。
	
 - 19.将字符串abcdefg逆序输出？
 - 
 - 20.Android中声明权限使用什么标签？
	<uses-permission /> 
 - 21.Activity的声明周期及横竖屏切换时的声明周期
	Activity starts(加载Activity)

	|-onCreate()
	|-onStart()
	|-onResume()横竖屏切换时候,(ctrl+F11)--->onPause()--->onStop()--->onDestroy()--->onCreate()--->onStart()--->onResume()
	
	#Activity is running(运行状态)

	Another activity comes in front of the activity(其他Activity转入前台)

	#|-onPause() 暂停状态
	
	If the Activity comes to the foreground,the other one will onResume
	Other application need memeory, Process is killed,User navigates back to the activity---->onCreate()
	更高优先级的程序应用需要内存---->应用程序被终止--->用户再次启动该Activity
	The activity is no longer visible(该Activity变为完全不可见)
	
	#|-onStop()停止状态
	
	The activity comes to the foreground---->onRestart()---->onStart()
	用户再次启动该Activity,是指进入前台
	Other application need memeory, Process is killed,User navigates back to the activity---->onCreate()
	更高优先级的程序应用需要内存---->应用程序被终止--->用户再次启动该Activity
	#|-onDestroy()销毁状态
	
	Activity is shut dowm.
	- 21.一个ScrollView中最多可以放几个Layout？最多一个


	- 22.Android中内存回收程序负责释放无用内存，其回收顺序依次是：EmptyProcess（空进程）BackgroundProcess（后台进程）ServiceProcess（服务进程）VisibleProcess（可见进程）ForeGroundProcess（进程）
	
	- 23.ArithmeticException（算数异常），IllegalArgumentException（无效的参数异常）,NullPointerException（空指针异常），BufferUnderFlowException，都属于运行时异常。
	
	- 24.对一些资源以及状态的操作保存，最好保存在生命周期的onStart()函数中。

	- 25.Intent传递数据时，Serializable,charsequence,Parcelable,Bundle
		
	- 26.Intent的作用，可以实现界面间的切换，可以包含动作和动作数据，连接四大组件的纽带。
	
	- 27.xml文件的三种解析方式：
	SAX：简单的说就是对文档进行顺序扫描，扫描到文档开始和结束或者元素开始和结束等地方的时候，通知时间处理函数，由事件处理函数做相应的动作，然后做继续的扫描，直到文档结束。它的有点事不用 事先调入整个文档，占用资源少。
	
	dom:DOM即一个文档对象模型，将整个XML文档载入内存，将每个节点当做一个对象。效率低不推荐使用。
	
	Pull:
	-  layout_gravity:设置控件本身相对于父控件的位置。gravity:设置控件内部内容的位置。。

	- 28.ContentValues和HashTable比较类似，负责存储一些键值对，但是ContentValues存储的键值对键是String类型，而值都是基本类型.
	- 29.线程销毁的方法是onDestroy()；
	- 30.退出Activity用finish(),抛异常强制退出，onstop(),不能用System.exit();
	- 31.Android中动画的分类是补间动画(Tween)和帧动画(Frame)
	- 32.DVK是Dalvik虚拟机，每个Android应用程序都在它自己的进程中运行，都拥有一个独立的Dalvik虚拟机实例，而每一个DVK都是Linux中的一个进程，所以说可以认为是同一个概念。
	- 33.Android项目工程下的assets目录的存放的事多媒体等数据文件。
	- 34.res/raw目录中的文件原封不动的储存在设备上不会转换为二进制格式。
	- 35.Android NDK是一系列工具的集合;提供了一份稳定的，功能有限的API头文件声明；是Java+C的开发方式转正，成为官方支持的开放方式；NDK将是Android平台支持C开发的开端。
	- 36.Android中的五大布局：Framelayout,Relativelayout,LinearLayout,TableLayout（表格布局）
	- 37.Android中service的实现方法是：startservice()和bindservice()
	- 38.Activity一般重载7个方法维护其声明周期，除了：onCreate()，onStart(),onDestory()还有：onRestart(),onResume(),onPause(),onStop()。
	- 39.Android中5种数据存储方式：sharedPreference,文件存储，SQLite,ContentProvider,网络存储。
	- 40.当启动一个Activity并且这个新的Activity执行完后需要返回到启动它的Activity来执行的回调函数是startActivityForResult();回调onActivityResult
	- 41.11页



	- 42.ListView的优化
	- 43.如何从服务器端获取大数据
	- 44.ListView和ScrollView的冲突
	- 45.两个Activity，A跳转到B(A跳转后没有调用finish),A跳转到B后，A还在任务栈中.
		当A跳转到B时，A和B的生命周期各是什么？
		A:onPause-----onStop
		B:onCreate----onStart----onResume
		按返回键后，页面到达A，此时A和B的生命周期又是什么？
		A:onRestart---onStart----onResume
		B:onPause----onStop----onDestroy

		如果调用finish()
		A:onPause-----onStop--onDestroy
		B:onCreate----onStart----onResume
		按back键
		A:
		B:onPause----onStop----onDestroy


	Oray面试题：
	- 1.简述Observer设计模式，并画出Observer的典型结构类图？
	对于java的观察者模式框架

	内置观察者模式主要有两个类，一个是Observable,一个是接口类Observer.
	
	抽象类Observable：
	
	被观察者要继承Observable类
	
	被观察者通知观察者时，也就是调用notifyObservers方法时，一定要先调用setChanged()方法
	
	Observable类的两个重载的notifyObservers方法，带参数的那个方法里面的参数就是Oberver接口种的update方法种的第二个参数
	
	接口类Observer：是观察者，只有一个接口方法public void update(Observalble arg0, Object arg1),需要其子类来实现。这里，Observable是被观察者对象，而arg是由notifyObervers（）方法传递的值。当被观测对象发生了改变，调用update()方法。
	


	- 2.在新的任务栈中启动一个Activity,intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	- 3.