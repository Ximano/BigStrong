package com.mili.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.hjq.toast.ToastUtils
import com.lazy.library.logging.Logcat
import com.mili.model.Sort
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author: Big Strong
 * @date  : 2019-06-05
 * @dec   : RxJava用法Debug测试
 * @example: 各种操作符的使用：https://www.jianshu.com/p/d997805b37d4 [包含github地址]
 */
class RxActivity : AppCompatActivity() {

    companion object {
        private const val TAG1 = "RX1"
        private const val TAG2 = "RX2"
        private const val TAG3 = "RX3"
        private const val TAG4 = "RX4"
        private const val TAG5 = "RX5"
        private const val TAG6 = "RX6"
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 创建一个被观察者「事件的发送者」
        val ober = Observable.create(ObservableOnSubscribe<Sort> { e ->
            val zs = Sort("张三", 1)
            val ls = Sort("李四", 2)
            e.onNext(zs)
            e.onNext(ls)
            e.onComplete()
        })

        // 创建一个观察者「事件的接收者，处理数据」
        val oser = object : Observer<Sort> {
            override fun onComplete() {
                Logcat.d(TAG1, "onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Logcat.d(TAG1, "onSubscribe")
            }

            override fun onNext(t: Sort) {
                Logcat.d(TAG1, "t.name == {${t.name}}")
            }

            override fun onError(e: Throwable) {
                Logcat.d(TAG1, "onError")
            }

        }

        // 被观察者订阅观察者
        ober.subscribe(oser)

        // 返回一个订阅的事件
        val disposable = ober.subscribe({
            Logcat.d(TAG2, "t.name == {${it.name}}")
        },
                {
                    Logcat.d(TAG2, "t.name == {${it.message}}")
                })

        // 将订阅事件添加到容器中
        compositeDisposable.add(disposable)

        chain1()

        chain2()

        transThread1()

        transThread2()
    }

    /**
     * 链式调用1
     */
    private fun chain1() {
        Observable.just("a")
                .map {
                    Log.d(TAG3, "map当前线程：${Thread.currentThread().name}； 被观察者的值 $it")
                    it + it
                }
                .subscribe(object : Observer<String> {
                    override fun onComplete() {
                        Log.d(TAG3, "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG3, "onSubscribe == ${d.isDisposed}")
                    }

                    override fun onNext(t: String) {
                        Log.d(TAG3, "onNext中的接收到的数据为 $t")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG3, "onNext中的接收到的数据为 ${e.message}")
                    }
                })
    }

    /**
     * 链式调用2 「emitter 发射器」
     */
    private fun chain2() {
        Observable.create(ObservableOnSubscribe<String> { e -> e.onNext("onNext") }
        ).filter { e -> e.contains("o") }
                .map { e -> "afterFirstFilter $e" }
                .filter { e -> e.contains("N") }
                .subscribe(object : Observer<String> {
                    override fun onComplete() {
                        Log.d(TAG4, "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG4, "onSubscribe == ${d.isDisposed}")
                    }

                    override fun onNext(t: String) {
                        Log.d(TAG4, "onNext中的接收到的数据为 $t")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG4, "onNext中的接收到的数据为 ${e.message}")
                    }
                })
    }

    /**
    subscribeOn: 指定Observable自身在哪个调度器上执行。
    subscribeOn这个操作符，与调用的位置无关，
    而且只有第一次调用时会指定Observable自己在哪个调度器执行。其实有一种情况特殊，
    就是在DoOnSubscribe操作符之后调用，可以使DoOnSubscribe在指定的调度器中执行。
     */
    @SuppressLint("CheckResult")
    private fun transThread1() {
        Observable.just(520)
                .map {
                    Log.d(TAG5, "map-1 当前线程为 ${Thread.currentThread().name}") // RxCachedThreadScheduler-2
                    return@map it
                }
                .subscribeOn(Schedulers.io())
                //                .subscribeOn(AndroidSchedulers.mainThread())
                //                .subscribeOn(Schedulers.newThread())
                .map {
                    Log.d(TAG5, "map-2 当前线程为 ${Thread.currentThread().name}") // RxCachedThreadScheduler-2
                    it
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .map {
                    Log.d(TAG5, "map-3 当前线程为 ${Thread.currentThread().name}") // RxCachedThreadScheduler-2
                    it
                }
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            Log.d(TAG5, "onNext $it 当前线程为 ${Thread.currentThread().name}") // RxCachedThreadScheduler-2
                        },
                        {
                            Log.d(TAG5, "onNext ${it.message}")
                        }
                )
    }

    /**
     * observeOn: 指定观察者在那个调度器上观察这个Observable
    每次调用了ObservableOn操作符之后，
    之后的Map和Subscribe操作符都会发生在指定的调度器中，实现了线程的切换。
     */
    @SuppressLint("CheckResult")
    private fun transThread2() {
        Observable.just(1)
                // 默认情况下是 .observeOn(AndroidSchedulers.mainThread())
                .map {
                    Log.d(TAG6, "map-1 当前线程为 ${Thread.currentThread().name}") // main
                    return@map it
                }
                .observeOn(Schedulers.newThread())
                .map {
                    Log.d(TAG6, "map-2 当前线程为 ${Thread.currentThread().name}") // RxNewThreadScheduler-1
                    it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    Log.d(TAG6, "map-3 当前线程为 ${Thread.currentThread().name}") // main
                    it
                }
                .observeOn(Schedulers.io())
                .subscribe(
                        {
                            Log.d(TAG6, "onNext $it 当前线程为 ${Thread.currentThread().name}") // RxCachedThreadScheduler-1
                        },
                        {
                            Log.d(TAG6, "onNext ${it.message}")
                        }
                )
    }


    override fun onDestroy() {
        super.onDestroy()
        Logcat.d("RX", "${compositeDisposable.isDisposed}")
        if (!compositeDisposable.isDisposed)
            compositeDisposable.clear()
    }
}