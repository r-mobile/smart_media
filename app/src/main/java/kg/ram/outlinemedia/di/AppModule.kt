package kg.ram.outlinemedia.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.ram.out_proxy.data.ProxyManager
import kg.ram.out_proxy.data.outline.OutlineConfigImpl
import kg.ram.out_proxy.data.outline.OutlineProxyImpl
import kg.ram.out_proxy.domain.AppProxy
import kg.ram.outlinemedia.network.FallbackConfigApi
import kg.ram.outlinemedia.network.SmartProxyApi
import kg.ram.outlinemedia.utils.Const
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

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
        return runBlocking { ProxyManager(proxy).also { manager -> manager.start() } }
    }
    //endregion

    //region Retrofit
    @Provides
    @Singleton
    fun provideHttpClient(manager: ProxyManager): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .proxy(manager.getProxy())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("fallback")
    fun provideFallbackRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Const.FALLBACK_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): SmartProxyApi {
        return retrofit.create(SmartProxyApi::class.java)
    }


    @Provides
    @Singleton
    fun provideFallbackApi(@Named("fallback") retrofit: Retrofit): FallbackConfigApi {
        return retrofit.create(FallbackConfigApi::class.java)
    }
    //endregion
}