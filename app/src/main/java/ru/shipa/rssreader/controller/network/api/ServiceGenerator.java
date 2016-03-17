package ru.shipa.rssreader.controller.network.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.shipa.rssreader.controller.network.NWConst;

/**
 * Created by Vlad on 14.03.2016.
 */
public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(NWConst.FULL_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create());

//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(API_BASE_URL)
//            .setClient(new OkHttpClient())
//            .addConverterFactory(SimpleXmlConverterFactory.create())
//            .build();
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }


    public static <S> S createService(Class<S> serviceClass, final String access_token) {
        if (access_token != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder()
                            // .header(NWConst.COOKIE, access_token)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}