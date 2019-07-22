# BigStrong进阶篇 2018年10月23日
 - [github地址](https://github.com/Ximano/Mili-Advance)
 - [HTTPS图片](https://coupon.i-liaoning.com.cn:2300/giftcenter/logo/jifen.png)

## RxAndroid `[可参照示例代码]`
 结合的观察者模式和链式处理 
 1. Observer：观察者。订阅者 Subscriber（订阅者是对观察者的扩展）。
 事件的接受者，也就是如何处理这个事件或者数据；
 2. Observable：被观察者。事件的发送者；
 3. subscribe()：被观察者订阅观察者，oble.subscribe(oser)返回一个Disposable（订阅状态的一个类，其中dispose()是主动解除订阅的方法，isDisposed()判断是否已经解除了订阅。在使用过程中，容易造成内存泄露，也就是订阅了事件之后没有及时的取消订阅，在View销毁的时候仍然占用着内存。）
 4. 解决内存泄露的两种方案：
 - 1. 手动在onError()和onComplete()方法调用后调用disposable.dispose()
 - 2. 每当创建一个订阅事件，将返回的Disposable添加到一个容器中CompositeDisposable中，在Base基类中，compositeDisposable.dispose()取消订阅，或者将compositeDisposable.add(disposable)，View销毁的时候compositeDisposable.clear()
 5. RxJava的链式调用：按照整个调用链来说，observable.subscribe(observer)前边就是被观察者，方法参数就是观察者对象。而subscribe（）前边的observable下的各种操作符，比如map,flatMap等，map前边的又可以作为被观察者，map后边的又可以作为被观察者。
 6. [大佬理解简书](https://www.jianshu.com/p/9253cbde19be)
> 首先了解下RxJava中的几种基本角色：
> Observable，是RxJava描述的事件流，可以说其与Observer构成了RxJava的基础。在链式调用中，事件从创建到加工到最后被Observer接受，其实就是由一条Observerable链串起来的。
> Observer，RxJava中的订阅者，也就是需要对事件进行响应的一个角色。其实除了我们通常自己实现的一个Observer，在链中的每一步都会产生一个Observer，然后这些Observer构成一条链，最终完成整个链的计算。
> ObservableOnSubscribe，整个事件流的源头，通常需要我们自己实现，其依赖一个Emitter。
> Emitter，可以将其理解为触发器，推动整个流程的运转。
> Scheduler，这个其实不用太过关心，RxJava用其封装了Thread，用于完成线程切换等任务。

7. observeOn：指定一个观察者在那个调度器上观察这个Observable。每次调用了ObservableOn操作符之后，之后的Map和Subscribe操作符都会发生在指定的调度器中，实现了线程的切换。
8. subscribeOn：这个Observable自身在哪个调度器上执行。SubscribeOn这个操作符，与调用的位置无关，而且只有第一次调用时会指定Observable自己在哪个调度器执行。其实有一种情况特殊，就是在DoOnSubscribe操作符之后调用，可以使DoOnSubscribe在指定的调度器中执行。

## MVP `[可参照示例代码]`
- M：model（模型）并不一定是数据类，而是按照规则返回的数据结构，比如返回的Observable<T>，可能包含Retrofit的请求。
- V：view（视图）并不一定是Activity，也可能是Fragment，View等。但是这些控件可能都需要实现一个IBaseView的接口，约束一个统一的规则，也便于多态中的泛型。比如统一的showLoading，dismissLoading，showError，showEmpty等。
- P：presenter（控制器）作用就是连接MV，使数据层(网络请求，IO操作，数据库读写)，视图层view解藕。
- 实现原理：P层持有V层的一个引用，View层实例化一个P层的实例，实现View层和Model层的节藕，从而实现真正的MVC即：MVP。请求数据格式化数据的具体实现都转移到Presenter中去操作，而Presenter中持有View（也就是Activity或者Fragment）的引用，直接调用View的方法对目标数据进行UI的展示，当然UI展示的具体实现还是在View的实例中去处理。这样做的好处就是View中只有请求数据，展示数据的动作，但是具体的实现其实是在Presenter中处理的。这样就达到了数据层Model，视图层View，控制层Presenter的分离。个人理解MVP核心就是用了，面相对象中的多肽和泛型的。或者说是一种回调的用法。避免一把梭，层次更加分明。而不是把整个程序混作一团。
- 缺点：虽然MVP是现今比较流行的架构，但是如果业务过于复杂也会导致P层太臃肿，而且P层和V层也有一定的耦合度，如果有任何UI的地方需要改，不只需要改P层，还需要改View的接口和实现，牵一发动全身的感觉。

## MVVM + Databinding
- M：负责数据实现和逻辑处理，与MVP中的M无异；
- V：对应Activity、Fragment和XML，负责View的绘制以及与用户的交互，与MVP中V无异；
- VM：创建关联，将Model和View绑定起来，实现View和Model间的交互和业务逻辑，基于databinding实现双向反馈。
- Databinding: 是一个实现数据和UI绑定的框架，它是实现MVVM模式的一个工具。由它来完成创建关联、数据绑定和自动刷新等一系列操作。MVVM中ViewModel和View进行了双向绑定，当ViewModel的数据发生变化的时候，自动的反应到View中显示，而当View对数据进行更改的时候ViewModel的数据也随之变化。
- MVVM和MVP的思想是一致的，但是没有MVP那么多的回调，通过Databinding就可以更新UI和状态。MVVM架构模式下，数据和业务逻辑都处于ViewModel中，ViewModel只关心数据和业务，不需要直接和UI打交道，而Model只需要提供ViewModel的数据源，View则关心如何显示数据和处理与用户的交互。


## Android 中内存泄漏的原因
- 生命周期较长的对象持有生命周期较短对象的引用 `Java中非静态内部类都会隐式的持有外部类的引用`
- 结合Handler进行线程间通信，造成内存泄漏的过程：不管是Handler发送延迟消息的时候，如果当这个消息还没有被接收
Activity就被finish的时候，因为handler是作为匿名内部类存在的，所以handler会持有Activity的引用。所以
Activity所占有的内存不能被释放，就造成了内存的泄漏。解决这种问题：如果一个非静态内部类的生命周期 > Activity的
生命周期的时候，不要使用非静态内部类 [隐式的持有Activity的引用无法手动干预]。要用静态内部类，手动的让Handler持
有Activity的引用，但是依旧解决不了问题。默认情况下Java对象的引用是强引用，Java的GC垃圾回收机制是
见到强引用绝不回收，遇到弱引用才会及时回收。所以我们将Handler对Activity持有弱引用，这样activity在finish的时
候，GC会及时回收Activity占用的资源。但是需要注意的是，Activity销毁的时候也要手动的释放掉Handler或者
定时器的一些资源，避免某些异步或者延迟回调时，出现其他的问题。
- 综上所述：Handler使用的时候避免内存泄漏的方案：
 - 1. 使用Handler的静态内部类
 - 2. Handler需要持有Activity的弱引用
- 可能造成内存泄露的其他情景：
 - 1. 单例。具有静态特性，其生命周期 == 应用的生命周期。单例在需要持有上下文的时候，必须持ApplicationContext
 级别的上下文，而不能使用Activity级别的上下文，所以在一些开源框架进行init的时候，大多都建议在Application中进行
 初始化并且传入applicationContext的上下文。
 - 2. 在Activity或Fragment使用非静态内部类，并且这个内部类的生命周期 > Activity的生命周期
 - 3. 需要手动关闭的对象，比如：IO操作中，数据库操作中；广播的注册和反注册；服务的停止；EventBus及时的反注册
 - 4. MVP中Presenter中持有View的引用，所以当Activity调用finish的时候，很容易造成Activity占用的资源无法释放。
 一般都会有在BaseActivity中统一接触关联，包括某些对象的置空。

## Handler实现线程间的通信
- 首先我们考虑下Handler存在的意义：既然是实现线程间的通信，那么我们在实际开发中遇到的使用场景那就是，我们常常
在工作线程处理一些事务，当到达某一个节点的时候需要通知主线程进行一个下一步的操作，通常来说就是子线程不能进行的
操作，比如：更新UI，需要在主线程中进行。那么问题来时，子线程如何通知主线程呢？这就是Handler存在的意义了。
Handler需要发送消息。发消息不是Handler自己能实现的，涉及到了另外几个概念包括：Looper，MessageQueue，
Message。
- 结合子线程向主线程发送消息来说的话，Handler是在主线程中定义的，调用handler.sendMessage()或handler.post
消息的时候，会把Message放进一个MessageQueue中，MessageQueue自身有放入消息和移出消息的功能。
这样的话，消息也发送了消息也被放进一个容器里边了，如何处理呢？Lopper的功能，Looper对象会对MessageQueue中的
消息进行无限循环的获取，如果有的话就发送给Handler，进行handlerMessage（），没有的话就阻塞。这样就完成了
Handler在两个线程中的通信。那前边提到的Looper对象，MessageQueue是什么时候创建的呢，一个进程在开启的时候，
Looper.prepare()方法中，Looper对象个MessageQueue对象已经被创建了，他们的生命周期是和主线程一致的。所以我们
在使用Handler进行通信的时候，不关注他们，是系统给我们初始化好的。

## ScrollView和ListView嵌套
- 现象：ListView只显示第一个Item。解决办法：重写ListView的onMessure()方法
- 现象：滑动冲突。原因：事件被ScrollView响应了，ListView根本没有触发触摸事件。
- View和ViewGroup事件分发机制：事件分发我们关注的三个方法
 - 1. dispatchTouchEvent()：事件分发。
 - 2. onIntercaptTouchEvent() ：事件拦截。只有ViewGroup有这个方法。返回true的时候，事件被拦截，
 事件不会分发给它的子View，也就是不回调用dispatchTouchEvent和onTouchEvent
 - 3. onTouchEvent() 事件消费。返回true表示事件被消费，他的父元素的onTouchEvent方法不会被调用。
 - 4. requestDisallowInterceptTouchEvent在实际解决滑动冲突的时候也有相当重要的作用。

## Activity的生命周期
- 1. onCreate() Activity被创建；
- 2. onStart() Activiyt可见但不可交互的状态；
 - Activity被onCreate()后立即执行onStart()；
 - 另外用户按下home键后，再切换到该Activity时：onStop() -> onRestart() -> onStart() -> onResume()
- 3. onResume() Activity可见且可与用户进行交互的状态。
- 4. onPause() Activity半可见状态；
 - 当前Activity被一个透明或者Dialog主题的Activity覆盖到时候，进入onPause(), 重新到前台直接onResume()
- 5. onStop() 完全不可见状态；
- 6. onDestroy 被销毁
场景：
- 1. A创建并且打开B：onCreate_A -> onStart_A -> onResume_A -> onPause_A -> onCreate_B -> onStart_B -> onResume_B -> onStop_A
     B再关闭：onPause_B -> onRestart_A -> onStart_A -> onResume_A -> onStop_B -> onDestroy_B
     
     
## 自定义View
- 1. 自制控件：继承自View或者ViewGroup，自己绘制；
 - onMessure()测量，onLayout()摆放，onDraw()摆放，分别是由messure()(是一个final类型的方法)，layout()可以重写，draw()
 可以重写。
 - View的measure()方法是对自身的测量，从而去调用onMessure()方法。而ViewGroup()的measure除了完成对自身的测量外，还要遍历去调用子View的measure()，个个子元素再去递归调用自身的measure()。如果是View，layout指定自己的位置，如果是ViewGroup()的话，onLayout()就是指定son.layout()的位置。draw的话大概就是：绘制背景backgroupDraw(canvas)，绘制自己onDraw()，绘制孩子dispatchDraw()，绘制装饰onDrawScrollBars()，我们主要关心就是onDraw()。
 - 如果是View的话，一般就是onDraw()和onLayout()，onMessure()。如果是ViewGroup()的话，onMessure()测量自身的话还要测量
 孩子，onLayout()还要摆放孩子的位置。
 - onFinishInflate实在xml加载组件完成后调用的。一般是在自定义ViewGroup的时候调用。
 - ViewGroup的getChildAt(int position)，返回该组中指定位置的实图。
 - onMeasure()的两个参数传的不单单是宽高的意思，传的是两个32位的二进制数。我们使用的是MeasureSpec--View中的静态内部类。
 这个类又getSize(),getMode(),getMessureSpec(int size, int mode),size占30位，mode占两位这样就返回了一个32位的二进制数。三种模式：EXACTLY精准的，AT_MOST最大值的，UNSPECIFIED未指定的。
 - EXACTLY：精准的一半是在xml文件中指定match_parent或者指定具体100dp;
 - AT_MOST：最大值的一半是xml中wrap_content;
 - UNSPECIFIED：未指定的。
- 2. 组合控件：利用系统控件，去组合一个新的控件；
- 3. 拓展控件：继承系统已经提供的控件，加上新的功能和特性。
 










