package kg.ram.out_proxy.domain

abstract class ProxyConfig<T> {
    abstract fun getConfig(): T
}