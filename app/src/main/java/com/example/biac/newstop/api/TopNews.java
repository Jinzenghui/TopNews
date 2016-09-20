package com.example.biac.newstop.api;

import com.example.biac.newstop.bean.NewsList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by BIAC on 2016/9/17.
 */
public interface TopNews {
    @GET("http://c.m.163.com/nc/article/headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNews(@Path("id") int id );
}
