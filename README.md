## Smart Proxy
The **Smart Proxy** library enables quick and efficient integration of media streaming capabilities into your application using a proxy to bypass content restrictions in your country.

Currently, the library utilizes [Outline Proxy](https://getoutline.org/) as its proxy solution. In future updates, we plan to add alternative proxy services for even greater flexibility. To facilitate seamless player implementation, the library provides extensions for [ExoPlayer](https://developer.android.com/media/media3/exoplayer), allowing you to stream media content effortlessly. The library is independent of the architecture or UI framework you are using.

## Installation
To integrate the library into your application, add the following repository:

***build.gradle***

    allprojects {
	    repositories {
	        maven { url "https://jitpack.io" }
	    }
	}
or **settings.gradle.kts**

    dependencyResolutionManagement {  
		...
	    repositories {  
		  ...
	      maven { url = uri("https://jitpack.io") }  
	    }
	}

Additionally, include the dependency in your project or module:

    implementation("com.github.roman-a-marchenko:outline_media:0.0.5")

You can find the latest version of the library on [JitPack](https://jitpack.io)

## Configuration

To enable proxy support in your application, you can either use the default _OutlineProxy_ settings or configure it with custom parameters.

The following example demonstrates how to initialize and start the proxy using Outline with its default configuration:

    fun getDefaultProxy(): AppProxy {
		val defaultMediaHost = "https://dwamdstream102.akamaized.net/"
	    val defaultConfig = OutlineConfigImpl.default(defaultMediaHost)  
	    return OutlineProxyImpl(defaultConfig)
	}

Next, create a proxy manager: 

    fun provideProxyManager(proxy: AppProxy): ProxyManager {  
	    return ProxyManager(proxy)  
	}

Once set up, you can interact with the **_ProxyManager_**, which provides the following methods:

    start() // Starts the proxy with the predefined configuration
    stop() // Stops the proxy
    updateConfig(config: ProxyConfig) // Updates the configuration and restarts the proxy
    getHost(): String // Returns the proxy host
    getPort(): Int  // Returns the proxy port
    getProxy(): Proxy // Returns a net.Proxy object for use in networking layers

Once the **_ProxyManager_** is configured, simply call _start()_, and the _Outline Proxy_ will be ready to use.

To enable media playback through _ExoPlayer_ using a proxy, apply the extension function to _ExoPlayer_:

    ExoPlayer.Builder(context).build().playStream(streamData, proxy)


The **_playStream(…)_** function accepts three parameters:

1.  An implementation of _StreamData_, which contains the stream URL and stream type.
2.  An implementation of _net.Proxy_ from _ProxyManager_.
3.  A _Boolean_ flag indicating whether the media should start playing immediately upon player readiness.

Once configured, media streaming will commence.

## Updating configuration

If you wish to apply custom settings to Outline, simply update the configuration using the _updateConfig(config: ProxyConfig)_ function within **_ProxyManager_**. The new configuration will be automatically applied, and the proxy will restart with the updated settings.

## Custom Proxies

The library allows you to implement custom proxies for your project and integrate them with **_ProxyManager_**. To do this, implement the _AppProxy_ interface and define the necessary methods.

Example:

    class MyCustomProxyImpl() : AppProxy {
	    suspend fun start() { ... }
	    suspend fun stop()  { ... }
	    suspend fun updateConfig(config: ProxyConfig<*>)  { ... }
	    fun getHost(): String?  { ... }
	    fun getPort(): Int?  { ... }
	    fun getConnectionStatus(): ConnectionStatus { ... }
    }

## Demo Application

The demo application showcases how to utilize the library for implementing a streaming service that plays DW English content.

Upon launch, the app applies the default settings and then retrieves necessary data from a server. The retrieved data includes user information, the stream URL, and the program schedule for the current day.

You can use the demo application as a foundation for building your own streaming app. Simply configure the app to connect to your server and adjust the data models as needed.
