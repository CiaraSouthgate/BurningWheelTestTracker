package com.ciarasouthgate.burningwheeltesttracker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.preferences.AppPreferences
import com.ciarasouthgate.burningwheeltesttracker.ui.list.CharacterListScreen
import com.ciarasouthgate.burningwheeltesttracker.ui.list.SkillListScreen
import com.ciarasouthgate.burningwheeltesttracker.ui.roll.RollDetail
import com.ciarasouthgate.burningwheeltesttracker.ui.skill.SkillEditor
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

private const val CHARACTERS = "characters"
private const val SKILLS = "skills"
private const val ROLL = "roll"
private const val SKILL_EDITOR = "skillEditor"

private const val CHARACTER_ID = "characterId"
private const val SKILL_ID = "skillId"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TrackerNavHost(
    appPreferences: AppPreferences,
    modifier: Modifier = Modifier
) {
    val navController = rememberAnimatedNavController()
    val startDestination = appPreferences.activeCharacterId.let {
        if (it == 0L) CHARACTERS else "$SKILLS/{$CHARACTER_ID}"
    }
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } }
    ) {
        composable(CHARACTERS) {
            CharacterListScreen(
                onCharacterAdded = navController::navigateToSkillList,
                onCharacterClicked = { character ->
                    appPreferences.activeCharacterId = character.id
                    navController.navigateToSkillList(character.id)
                }
            )
        }
        composable(
            "$SKILLS/{$CHARACTER_ID}",
            arguments = listOf(
                navArgument(CHARACTER_ID) {
                    type = NavType.LongType
                    defaultValue = appPreferences.activeCharacterId
                }
            )
        ) { entry ->
            val characterId = entry.arguments?.getLong(CHARACTER_ID)
                ?: throw IllegalArgumentException("Must provide character ID for skills list")

            SkillListScreen(
                characterId = characterId,
                onAddClicked = {
                    navController.navigate(
                        "${SKILL_EDITOR}/${characterId}"
                    )
                },
                onSkillClicked = {
                    navController.navigate(
                        "${ROLL}/${it.id}"
                    )
                },
                onSkillEdit = { skill ->
                    navController.navigate("$SKILL_EDITOR/$characterId?$SKILL_ID=${skill.id}")
                },
                navigationIcon = {
                    BackButton(navController) {
                        appPreferences.activeCharacterId = 0
                        navController.navigate(CHARACTERS)
                    }
                }
            )
        }
        composable(
            "$ROLL/{$SKILL_ID}",
            arguments = listOf(
                navArgument(SKILL_ID) {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            val skillId = entry.arguments?.getLong(SKILL_ID)
                ?: throw IllegalArgumentException("Missing skill ID")
            RollDetail(
                skillId,
                navigationIcon = { BackButton(navController) },
                onSave = navController::popBackStack
            )
        }
        composable(
            "$SKILL_EDITOR/{$CHARACTER_ID}?$SKILL_ID={$SKILL_ID}",
            arguments = listOf(
                navArgument(CHARACTER_ID) {
                    type = NavType.LongType
                },
                navArgument(SKILL_ID) {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val characterId = entry.arguments?.getLong(CHARACTER_ID)
                ?: throw IllegalArgumentException("Must provide character ID to add skill")
            val skillId = entry.arguments?.getString(SKILL_ID)?.toLong()
            SkillEditor(
                characterId = characterId,
                skillId = skillId,
                navigationIcon = { BackButton(navController) },
                onSkillSaved = { navController.navigateToSkillList(characterId) }
            )
        }
    }
}

@Composable
fun BackButton(
    navController: NavHostController,
    onClick: () -> Unit = { navController.popBackStack() }
) {
    IconButton(onClick) {
        Icon(Icons.Default.ArrowBack, stringResource(R.string.back))
    }
}

private fun NavHostController.navigateToSkillList(characterId: Long) =
    navigate("$SKILLS/$characterId")