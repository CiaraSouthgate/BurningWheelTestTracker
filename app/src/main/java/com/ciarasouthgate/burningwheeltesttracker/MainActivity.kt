package com.ciarasouthgate.burningwheeltesttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ciarasouthgate.burningwheeltesttracker.navigation.TrackerNavHost
import com.ciarasouthgate.burningwheeltesttracker.ui.list.SkillListViewModel
import com.ciarasouthgate.burningwheeltesttracker.ui.roll.RollDetailViewModel
import com.ciarasouthgate.burningwheeltesttracker.ui.skill.SkillEditorViewModel
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestTrackerTheme {
                TrackerNavHost()
            }
        }
    }

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun skillListViewModelFactory(): SkillListViewModel.Factory
        fun rollDetailViewModelFactory(): RollDetailViewModel.Factory
        fun skillEditorViewModelFactory(): SkillEditorViewModel.Factory
    }
}