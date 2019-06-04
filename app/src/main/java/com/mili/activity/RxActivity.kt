package com.mili.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hjq.toast.ToastUtils
import com.lazy.library.logging.Logcat
import com.mili.model.Sort
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RxActivity : AppCompatActivity() {
    val compositeDisposable = CompositeDisposable()
//    var disposable: Disposable? = null
    @SuppressLint("CheckResult")
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
                Logcat.d("RX", "onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Logcat.d("RX", "onSubscribe")
            }

            override fun onNext(t: Sort) {
                Logcat.d("RX", "t.name == {${t.name}}")
                ToastUtils.show("t.name == {${t.name}}")
            }

            override fun onError(e: Throwable) {
                Logcat.d("RX", "onError")
            }

        }

        // 被观察者订阅观察者
        ober.subscribe(oser)

        val disposable = ober.subscribe({
            Logcat.d("RX", "t.name == {${it.name}}")
        },
                {
                    Logcat.d("RX", "t.name == {${it.message}}")
                })
        // 将订阅事件添加到容器中
        compositeDisposable.add(disposable)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed)
            compositeDisposable.clear()
    }
}