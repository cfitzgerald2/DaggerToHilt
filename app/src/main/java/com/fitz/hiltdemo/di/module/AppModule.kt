package com.fitz.hiltdemo.di.module

import android.content.Context
import android.os.CountDownTimer
import com.fitz.movie.persistence.db.MovieDatabase
import com.fitz.movie.usecase.logger.AppLogger
import com.fitz.movie.usecase.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    fun provideApplication(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideDatabase(context: Context) : MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }

    @Provides
    fun provideTimer(@AppLogger logger: Logger): CountDownTimer {
        return object: CountDownTimer(10000, 5000) {
            override fun onTick(p0: Long) {
                logger.log("timer tick")
            }

            override fun onFinish() {
                logger.log("timer finished")
                this.start()
            }
        }
    }
}