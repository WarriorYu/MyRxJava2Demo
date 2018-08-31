package com.demo.myrxandroiddemo.activity;

import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.demo.myrxandroiddemo.Apple;
import com.demo.myrxandroiddemo.R;
import com.demo.myrxandroiddemo.model.BananaModel;
import com.demo.myrxandroiddemo.model.Fruit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //testCreate();
               /* 打印日志
                com.demo.myrxandroiddemo E/Disposable---: false
                com.demo.myrxandroiddemo E/onNext---value---: 1
                com.demo.myrxandroiddemo E/emitter---: 1
                com.demo.myrxandroiddemo E/onNext---value---: 2
                com.demo.myrxandroiddemo E/onNext---disposable---: true
                com.demo.myrxandroiddemo E/emitter---: 2
                com.demo.myrxandroiddemo E/emitter---: 3
                com.demo.myrxandroiddemo E/emitter---: 4*/

                //testMap();
               /* 打印日志
                com.demo.myrxandroiddemo E/map---: 通过map我变成了10
                com.demo.myrxandroiddemo E/map---: 通过map我变成了20
                com.demo.myrxandroiddemo E/map---: 通过map我变成了30*/

                //testZip();
               /* 打印日志
                com.demo.myrxandroiddemo E/String--emitter---: A
                com.demo.myrxandroiddemo E/String--emitter---: B
                com.demo.myrxandroiddemo E/String--emitter---: C
                com.demo.myrxandroiddemo E/accept---: A1
                com.demo.myrxandroiddemo E/Integer--emitter---: 1
                com.demo.myrxandroiddemo E/accept---: B2
                com.demo.myrxandroiddemo E/Integer--emitter---: 2
                com.demo.myrxandroiddemo E/accept---: C3
                com.demo.myrxandroiddemo E/Integer--emitter---: 3
                com.demo.myrxandroiddemo E/Integer--emitter---: 4
                com.demo.myrxandroiddemo E/Integer--emitter---: 5*/

                //testConcat();
               /* 打印日志
                com.demo.myrxandroiddemo E/concat--: 1
                com.demo.myrxandroiddemo E/concat--: 2
                com.demo.myrxandroiddemo E/concat--: 3
                com.demo.myrxandroiddemo E/concat--: 4
                com.demo.myrxandroiddemo E/concat--: 5
                com.demo.myrxandroiddemo E/concat--: 6
                com.demo.myrxandroiddemo E/concat--: 7 */

                //testFlatMap();
                /*打印日志
                08-31 18:46:37.855 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value3
                08-31 18:46:37.855 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value3
                08-31 18:46:37.855 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value3
                08-31 18:46:37.857 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value1
                08-31 18:46:37.858 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value2
                08-31 18:46:37.858 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value2
                08-31 18:46:37.858 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value2
                08-31 18:46:37.859 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value1
                08-31 18:46:37.859 16673-16673/com.demo.myrxandroiddemo E/accept--=: I am value1*/

                testConcatMap();
                /*打印日志
                com.demo.myrxandroiddemo E/accept--=: I am value1
                com.demo.myrxandroiddemo E/accept--=: I am value1
                com.demo.myrxandroiddemo E/accept--=: I am value1
                com.demo.myrxandroiddemo E/accept--=: I am value2
                com.demo.myrxandroiddemo E/accept--=: I am value2
                com.demo.myrxandroiddemo E/accept--=: I am value2
                com.demo.myrxandroiddemo E/accept--=: I am value3
                com.demo.myrxandroiddemo E/accept--=: I am value3
                com.demo.myrxandroiddemo E/accept--=: I am value3*/
            }
        });
    }

    private void testCreate() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Log.e("emitter---", "1");
                emitter.onNext(2);
                Log.e("emitter---", "2");
                emitter.onNext(3);
                Log.e("emitter---", "3");
                //emitter.onComplete();// 调用onComplete()后，下面的 emitter.onNext(4)会继续发送事件，但是无法接收事件了；
                emitter.onNext(4);
                Log.e("emitter---", "4");
            }
        }).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDispisable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e("Disposable---", d.isDisposed() + "");
                mDispisable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e("onNext---value---", integer + "");
                i++;
                if (i == 2) {
                    mDispisable.dispose();//当调用dispose()后，接收器停止了接收事件，可以通过此方法动态控制接收事件
                    Log.e("onNext---disposable---", mDispisable.isDisposed() + "");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("onComplete", "---onComplete");
            }
        });
    }

    private void testMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

            }
        }).map(new Function<Integer, String>() {//map的作用就是将Observable通过某种函数关系，转换为另一中Observable,此例中将Interger->String
            @Override
            public String apply(Integer integer) throws Exception {
                return "通过map我变成了" + integer * 10;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("map---", s);
            }
        });
    }

    private void testZip() {
        Observable.zip(getStringObservable(), getObservableInteger(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("accept---", s);
            }
        });
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    emitter.onNext("A");
                    Log.e("String--emitter---", "A");
                    emitter.onNext("B");
                    Log.e("String--emitter---", "B");
                    emitter.onNext("C");
                    Log.e("String--emitter---", "C");
                }
            }
        });
    }

    private Observable<Integer> getObservableInteger() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    emitter.onNext(1);
                    Log.e("Integer--emitter---", "1");
                    emitter.onNext(2);
                    Log.e("Integer--emitter---", "2");
                    emitter.onNext(3);
                    Log.e("Integer--emitter---", "3");
                    emitter.onNext(4);
                    Log.e("Integer--emitter---", "4");
                    emitter.onNext(5);
                    Log.e("Integer--emitter---", "5");
                }
            }
        });
    }

    private void testConcat() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6, 7)).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("concat--", integer + "");
            }
        });
    }

    private void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value" + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);//添加了延时，证明事件的无序性，和concatMap作区分。flatMap 并不能保证事件的顺序，如果需要保证，需要用下面的 ConcatMap。
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("accept--=", s);
                    }
                });
    }
    private void testConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value" + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);//添加了延时，证明事件的无序性，和flatMap作区分。flatMap 并不能保证事件的顺序。
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("accept--=", s);
                    }
                });
    }

}
