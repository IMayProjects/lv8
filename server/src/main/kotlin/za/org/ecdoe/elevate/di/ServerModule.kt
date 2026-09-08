package za.org.ecdoe.elevate.di

import org.koin.dsl.koinConfiguration
import za.org.ecdoe.data.di.dataModule


val koinConfig = koinConfiguration {
    modules(dataModule())
}