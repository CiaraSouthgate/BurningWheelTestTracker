package com.ciarasouthgate.burningwheeltesttracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ciarasouthgate.burningwheeltesttracker.data.SkillRepository
import com.ciarasouthgate.burningwheeltesttracker.roll.rememberRollState
import com.ciarasouthgate.burningwheeltesttracker.ui.list.CharacterList
import com.ciarasouthgate.burningwheeltesttracker.ui.list.SkillList
import com.ciarasouthgate.burningwheeltesttracker.ui.roll.RollDetail

@Composable
fun TrackerNavHost(
    navController: NavHostController,
    repository: SkillRepository,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.CHARACTER_LIST.name,
        modifier = modifier
    ) {
        composable(Screens.CHARACTER_LIST.name) {
            CharacterList(
                characters = repository.characters,
                onCharacterClicked = { character ->
                    navController.navigate("${Screens.SKILLS_LIST.name}/${character.name}")
                }
            )
        }
        composable(
            Screens.SKILLS_LIST.name,
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
            val skills = repository.getSkillsForCharacter(characterName)
            SkillList(
                skills = skills,
                onSkillClicked = {
                    navController.navigate(
                        "${Screens.ROLL_DETAIL.name}/${it.characterName}/${it.name}"
                    )
                }
            )
        }
        composable(
            Screens.ROLL_DETAIL.name,
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
            val skill = repository.getSkill(characterName, skillName)
            val rollState = rememberRollState(skill)
            RollDetail(rollState)
        }
    }
}

private const val CHARACTER_NAME = "characterName"
private const val SKILL_NAME = "skillName"

private enum class Screens {
    CHARACTER_LIST,
    SKILLS_LIST,
    ROLL_DETAIL
}