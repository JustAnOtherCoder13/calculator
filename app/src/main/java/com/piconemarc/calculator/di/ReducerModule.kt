package com.piconemarc.calculator.di

import com.piconemarc.calculator.reducer.*
import com.piconemarc.calculator.utils.interfaces.DefaultStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.internal.processedrootsentinel.ProcessedRootSentinel
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ReducerModule {

    @Singleton
    @Provides
    fun provideGlobalStore(state: GlobalState): DefaultStore<GlobalState> {
        return DefaultStore(
            initialState = state,
            reducer = appReducer
        )
    }

    @Provides
    fun provideGlobalState(
        homeState: HomeState,
        gameState: GameState,
        gameOverState: GameOverState,
        topTenState: TopTenState
    ): GlobalState = GlobalState(
        homeState, gameState, gameOverState, topTenState
    )

    @Provides
    fun homeState() : HomeState = HomeState()

    @Provides
    fun gameState() :GameState = GameState()

    @Provides
    fun gameOverStates() : GameOverState = GameOverState()

    @Provides
    fun topTenState() : TopTenState = TopTenState()

}