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
> 
> Observable，是RxJava描述的事件流，可以说其与Observer构成了RxJava的基础。在链式调用中，事件从创建到加工到最后被Observer接受，其实就是由一条Observerable链串起来的。
> 
> Observer，RxJava中的订阅者，也就是需要对事件进行响应的一个角色。其实除了我们通常自己实现的一个Observer，在链中的每一步都会产生一个Observer，然后这些Observer构成一条链，最终完成整个链的计算。
> 
> ObservableOnSubscribe，整个事件流的源头，通常需要我们自己实现，其依赖一个Emitter。
> 
> Emitter，可以将其理解为触发器，推动整个流程的运转。
> 
> Scheduler，这个其实不用太过关心，RxJava用其封装了Thread，用于完成线程切换等任务。

7. observeOn：指定一个观察者在那个调度器上观察这个Observable。每次调用了ObservableOn操作符之后，之后的Map和Subscribe操作符都会发生在指定的调度器中，实现了线程的切换。
8. subscribeOn：这个Observable自身在哪个调度器上执行。SubscribeOn这个操作符，与调用的位置无关，而且只有第一次调用时会指定Observable自己在哪个调度器执行。其实有一种情况特殊，就是在DoOnSubscribe操作符之后调用，可以使DoOnSubscribe在指定的调度器中执行。

## MVP
- M：model（模型）并不一定是数据类，而是按照规则返回的数据结构，比如返回的Observable<T>，可能包含Retrofit的请求。
- V：view（视图）并不一定是Activity，也可能是Fragment，View等。但是这些控件可能都需要实现一个IBaseView的接口，约束一个统一的规则，也便于多态中的泛型。比如统一的showLoading，dismissLoading，showError，showEmpty等。
- P：presenter（控制器）作用就是连接MV，使数据层(网络请求，IO操作，数据库读写)，视图层view解藕。
避免一把锁，层次更加分明。而不是把整个程序混作一团。












