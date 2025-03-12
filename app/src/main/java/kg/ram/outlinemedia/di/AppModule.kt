package kg.ram.outlinemedia.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.ram.out_proxy.data.ProxyManager
import kg.ram.out_proxy.data.outline.OutlineConfigImpl
import kg.ram.out_proxy.data.outline.OutlineProxyImpl
import kg.ram.out_proxy.domain.AppProxy
import kg.ram.outlinemedia.network.SmartProxyApi
import kg.ram.outlinemedia.utils.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    //region Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .client(okHttp)
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): SmartProxyApi {
        return retrofit.create(SmartProxyApi::class.java)
    }
    //endregion

    //region Proxy
    @Provides
    @Singleton
    fun provideOutlineProxy(): AppProxy {
        val defaultConfig = OutlineConfigImpl.default(Const.DEFAULT_URL)
        return OutlineProxyImpl(defaultConfig)
    }

    @Provides
    @Singleton
    fun provideProxyManager(proxy: AppProxy): ProxyManager {
        return ProxyManager(proxy)
    }
    //endregion
}