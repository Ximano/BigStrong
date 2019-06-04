# BigStrong进阶篇 2018年10月23日
 - [github地址](https://github.com/Ximano/Mili-Advance)
 - [HTTPS图片](https://coupon.i-liaoning.com.cn:2300/giftcenter/logo/jifen.png)

 ## RxAndroid `[可参照示例代码]`
 1. Observer：观察者。订阅者 Subscriber（订阅者是对观察者的扩展）。
 事件的接受者，也就是如何处理这个事件或者数据；
 2. Observable：被观察者。事件的发送者；
 3. subscribe()：被观察者订阅观察者，oble.subscribe(oser)返回一个Disposable（订阅状态的一个类，其中dispose()是主动解除订阅的方法，isDisposed()判断是否已经解除了订阅。在使用过程中，容易造成内存泄露，也就是订阅了事件之后没有及时的取消订阅，在View销毁的时候仍然占用着内存。）
 4. 解决内存泄露的两种方案：
 - 1. 手动在onError()和onComplete()方法调用后调用disposable.dispose()
 - 2. 每当创建一个订阅事件，将返回的Disposable添加到一个容器中CompositeDisposable中，在Base基类中，compositeDisposable.dispose()取消订阅，或者将compositeDisposable.add(disposable)，View销毁的时候compositeDisposable.clear()
