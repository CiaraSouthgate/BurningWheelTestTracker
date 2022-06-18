package com.ciarasouthgate.burningwheeltesttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ciarasouthgate.burningwheeltesttracker.data.SkillRepository
import com.ciarasouthgate.burningwheeltesttracker.db.getDatabase
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var skillRepository: SkillRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        skillRepository = SkillRepository(getDatabase(this))

        setContent {
            TestTrackerTheme {
                val navController = rememberNavController()

            }
        }
    }
}