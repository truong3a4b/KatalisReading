package com.nxt.katalisreading.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nxt.katalisreading.data.repository.AuthRepo
import com.nxt.katalisreading.domain.repository.IAuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class provideAuthRepositoryModule() {
//
//    @Binds
//    @Singleton
//    abstract fun bindIAuthRepository(impl: AuthRepo): IAuthRepository
//}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): IAuthRepository {
        return AuthRepo(
            firebaseAuth = FirebaseAuth.getInstance(),
            realtimeDb = FirebaseDatabase.getInstance().reference
        )
    }
}