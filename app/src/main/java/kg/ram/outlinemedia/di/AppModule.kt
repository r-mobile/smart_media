package kg.ram.outlinemedia.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kg.ram.out_proxy.data.ProxyManager
import kg.ram.out_proxy.data.outline.OutlineConfigImpl
import kg.ram.out_proxy.data.outline.OutlineProxyImpl
import kg.ram.out_proxy.domain.AppProxy
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Provides
    @Singleton
    fun provideDefaultConfig(@ApplicationContext context: Context): String {
        context.assets.open("config.json")
            .bufferedReader()
            .use { return it.readText() }
    }

    @Provides
    fun provideOutlineConfig(defConfigJson: String): OutlineConfigImpl {
        return OutlineConfigImpl.default(defConfigJson)
    }

    @Provides
    fun provideOutlineProxy(config: OutlineConfigImpl): AppProxy {
        return OutlineProxyImpl(config)
    }

    @Provides
    fun provideProxyManager(proxy: AppProxy): ProxyManager {
        return ProxyManager(proxy)
    }
}