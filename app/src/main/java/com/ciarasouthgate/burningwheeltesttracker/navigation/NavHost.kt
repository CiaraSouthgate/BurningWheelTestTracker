package com.ciarasouthgate.burningwheeltesttracker.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.list.CharacterListScreen
import com.ciarasouthgate.burningwheeltesttracker.ui.list.SkillListScreen
import com.ciarasouthgate.burningwheeltesttracker.ui.roll.RollDetail
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
fun TrackerNavHost(modifier: Modifier = Modifier) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = CHARACTERS,
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
                    navController.navigateToSkillList(character.id)
                }
            )
        }
        composable(
            "${SKILLS}/{$CHARACTER_ID}",
            arguments = listOf(
                navArgument(CHARACTER_ID) {
                    type = NavType.LongType
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
                navigationIcon = { BackButton(navController) }
            )
        }
        composable(
            "${ROLL}/{$SKILL_ID}",
            arguments = listOf(
                navArgument(SKILL_ID) {
                    type = NavType.LongType
                }
            )
        ) { entry ->
            val skillId = entry.arguments?.getLong(SKILL_ID)
                ?: throw IllegalArgumentException("Missing skill ID")
            RollDetail(skillId)
        }
        composable(
            "${SKILL_EDITOR}/{$CHARACTER_ID}",
            arguments = listOf(
                navArgument(CHARACTER_ID) {
                    type = NavType.LongType
                },
                navArgument(SKILL_ID) {
                    type = NavType.LongType
                    defaultValue = null
                }
            )
        ) { entry ->
            val characterId = entry.arguments?.getLong(CHARACTER_ID)
                ?: throw IllegalArgumentException("Must provide character ID to add skill")
            val skillId = entry.arguments?.getLong(SKILL_ID)
            // TODO add skill screen
        }
    }
}

@Composable
fun BackButton(navController: NavHostController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(Icons.Default.ArrowBack, stringResource(R.string.back))
    }
}

private fun NavHostController.navigateToSkillList(characterId: Long) =
    navigate("${SKILLS}/${characterId}")