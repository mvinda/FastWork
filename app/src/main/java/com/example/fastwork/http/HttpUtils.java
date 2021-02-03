package com.example.fastwork.http;

import com.maka.app.util.cache.Cache;
import com.maka.app.util.cache.RetrofitServiceCache;
import dagger.Lazy;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;


public class HttpUtils implements IHttpUtils {

    Lazy<Retrofit> mRetrofit;

    Cache.Factory mCacheFactory;

    private Cache<String, Object> mRetrofitServiceCache;

    public HttpUtils() {
    }

    @Override
    public <T> T createApi(@NotNull Class<T> service) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory.build(new RetrofitServiceCache());
        }
        T retrofitService = (T) mRetrofitServiceCache.get(service.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.get().create(service);
            mRetrofitServiceCache.put(service.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }
}
