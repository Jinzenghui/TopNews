package com.example.biac.newstop.presenter.implPresenter;

import com.example.biac.newstop.bean.NewsDetailBean;
import com.example.biac.newstop.presenter.INewTopDescriblePresenter;
import com.example.biac.newstop.presenter.implView.ITopNewsDesFragment;
import com.example.biac.newstop.util.NewsJsonUtils;
import com.example.biac.newstop.util.OkHttpUtils;
import com.example.biac.newstop.util.Urls;

/**
 * Created by BIAC on 2016/9/21.
 */
public class TopNewsDesPresenterImpl extends BasePresenterImpl implements INewTopDescriblePresenter {
    private ITopNewsDesFragment mITopNewsFragment;

    public TopNewsDesPresenterImpl(ITopNewsDesFragment topNewsFragment) {
        if (topNewsFragment == null)
            throw new IllegalArgumentException(" must not be null");
        mITopNewsFragment = topNewsFragment;
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }

    @Override
    public void getDescrible(final String docid) {
        mITopNewsFragment.showProgressDialog();
        String url = getDetailUrl(docid);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, docid);
                mITopNewsFragment.upListItem(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                mITopNewsFragment.showError(e.toString());
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);

    }
}
