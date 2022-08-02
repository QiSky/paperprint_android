package com.skyqi.module_login.login.view_model;///

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/10 2:17 下午
/// * @Description: 文件说明
///
public class SmsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isPostLiveData = new MutableLiveData<>();

    private final MutableLiveData<Integer> secondsLiveData = new MutableLiveData<>();

    private Disposable mDisposable;

    public MutableLiveData<Boolean> getIsPostLiveData() {
        return isPostLiveData;
    }

    public void setIsPostLiveData(boolean result) {
        isPostLiveData.postValue(result);
    }

    public MutableLiveData<Integer> getSecondsLiveData() {
        return secondsLiveData;
    }

    public void setSecondsLiveData(int result) {
        secondsLiveData.postValue(result);
    }

    public void startTimeCount(LifecycleOwner lifecycleOwner) {
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        int data = getSecondsLiveData().getValue();
                        if (data > 0) {
                            getSecondsLiveData().postValue(--data);
                        } else {
                            getIsPostLiveData().postValue(true);
                            if(mDisposable != null) {
                                mDisposable.dispose();
                            }
                        }
                    }
                });
    }
}
