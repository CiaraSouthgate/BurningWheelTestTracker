package com.ciarasouthgate.burningwheeltesttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ciarasouthgate.burningwheeltesttracker.navigation.TrackerNavHost
import com.ciarasouthgate.burningwheeltesttracker.preferences.AppPreferences
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.RollDetailViewModelImpl
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.SkillEditorViewModel
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.SkillListViewModelImpl
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                TrackerNavHost(appPreferences)
            }
        }
    }

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun skillListViewModelFactory(): SkillListViewModelImpl.Factory
        fun rollDetailViewModelFactory(): RollDetailViewModelImpl.Factory
        fun skillEditorViewModelFactory(): SkillEditorViewModel.Factory
    }
}