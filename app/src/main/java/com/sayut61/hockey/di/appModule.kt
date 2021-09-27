package com.sayut61.hockey.di

import android.content.Context
import androidx.room.Room
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GameDB
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.TeamDB
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.TeamsInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.repositories.CalendarRepositoriesImpl
import com.sayut61.hockey.datalayer.repositories.TeamRepositoriesImpl
import com.sayut61.hockey.domain.CalendarRepositories
import com.sayut61.hockey.domain.TeamRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//
//@InstallIn(SingletonComponent::class)
//@Module
//object AppModule {
//
//}

@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    @Singleton
    @Provides
    fun provideTeamsInfoDao(@ApplicationContext context: Context): TeamsInfoDao {
        val db = Room.databaseBuilder(context, TeamDB::class.java, "teamDb").build()
        return db.teamsInfoDao()
    }
    @Singleton
    @Provides
    fun provideGamesInfoDao(@ApplicationContext context: Context): GamesInfoDao {
        val db = Room.databaseBuilder(context, GameDB::class.java, "gameDb").build()
        return db.gamesInfoDao()
    }
    @Singleton
    @Provides
    fun providesTeamRepositories(remoteDataSource: RemoteDataSource, teamsInfoDao: TeamsInfoDao): TeamRepositories{
        return TeamRepositoriesImpl(remoteDataSource, teamsInfoDao)
    }
    @Singleton
    @Provides
    fun providesCalendarRepositories(remoteDataSource: RemoteDataSource, gamesInfoDao: GamesInfoDao): CalendarRepositories{
        return CalendarRepositoriesImpl(remoteDataSource, gamesInfoDao)
    }
}

