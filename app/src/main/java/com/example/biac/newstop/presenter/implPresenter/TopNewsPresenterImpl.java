package com.example.biac.newstop.presenter.implPresenter;

import android.util.Log;

import com.example.biac.newstop.api.ApiManage;
import com.example.biac.newstop.bean.NewsList;
import com.example.biac.newstop.presenter.INewTopPresenter;
import com.example.biac.newstop.presenter.implView.ITopNewsFragment;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by BIAC on 2016/9/17.
 */
public class TopNewsPresenterImpl extends BasePresenterImpl implements INewTopPresenter{

    ITopNewsFragment mITopNewsFragment;

    public TopNewsPresenterImpl(ITopNewsFragment iTopNewsFragment){
        this.mITopNewsFragment = iTopNewsFragment;
    }

    @Override
    public void getNewsList(int t) {
        mITopNewsFragment.showProgressDialog();
        Subscription subscription = ApiManage.getInstence().getTopNewsService().getNews(t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mITopNewsFragment.hidProgressDialog();
                        mITopNewsFragment.showError(e.toString());
                        Log.d("TAG", e.toString());
                    }

                    @Override
                    public void onNext(NewsList newsList) {
                        mITopNewsFragment.hidProgressDialog();
                        mITopNewsFragment.upListItem(newsList);
                    }
                });

        addSubscription(subscription);
    }
}
