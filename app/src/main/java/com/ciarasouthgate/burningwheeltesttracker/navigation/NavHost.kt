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

private const val CHARACTER_NAME = "characterName"
private const val SKILL_NAME = "skillName"

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
                    navController.navigateToSkillList(character.name)
                }
            )
        }
        composable(
            "${SKILLS}/{$CHARACTER_NAME}",
            arguments = listOf(
                navArgument(CHARACTER_NAME) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val characterName = entry.arguments?.getString(CHARACTER_NAME)
            if (characterName.isNullOrEmpty()) {
                throw IllegalArgumentException("Must provide character name for skills list")
            }

            SkillListScreen(
                characterName = characterName,
                onAddClicked = {
                    navController.navigate(
                        "${SKILL_EDITOR}/${characterName}"
                    )
                },
                onSkillClicked = {
                    navController.navigate(
                        "${ROLL}/${it.characterName}/${it.name}"
                    )
                },
                navigationIcon = { BackButton(navController) }
            )
        }
        composable(
            "${ROLL}/{$CHARACTER_NAME}/{$SKILL_NAME}",
            arguments = listOf(
                navArgument(CHARACTER_NAME) {
                    type = NavType.StringType
                },
                navArgument(SKILL_NAME) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val characterName = entry.arguments?.getString(CHARACTER_NAME)
            val skillName = entry.arguments?.getString(SKILL_NAME)
            if (characterName.isNullOrEmpty() || skillName.isNullOrEmpty()) {
                throw IllegalArgumentException("Missing character or skill name")
            }
            RollDetail(characterName, skillName)
        }
        composable(
            "${SKILL_EDITOR}/{$CHARACTER_NAME}",
            arguments = listOf(
                navArgument(CHARACTER_NAME) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val characterName = entry.arguments?.getString(CHARACTER_NAME)
            if (characterName.isNullOrEmpty()) {
                throw IllegalArgumentException("Must provide character name to add skill")
            }
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

private fun NavHostController.navigateToSkillList(characterName: String) =
    navigate("${SKILLS}/${characterName}")