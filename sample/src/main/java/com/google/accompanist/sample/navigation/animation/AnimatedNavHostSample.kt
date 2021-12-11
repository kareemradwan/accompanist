/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.accompanist.sample.navigation.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.sample.AccompanistSampleTheme

/**
 * This Enum class represents screens name and identifier for screens graph.
 * @param label used on buttons or views.
 * @param identifier used on navigation code to detect any screen need to use.
 * @author Kareem Radwan
 */
enum class Screens(val label: String, val identifier: String) {

    RED("Red", "red"),
    BLUE("Blue", "blue"),
    GREEN("Green" ,"green");

}

@ExperimentalAnimationApi
class AnimatedNavHostSample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AccompanistSampleTheme {
                ExperimentalAnimationNav()
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ExperimentalAnimationNav() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = Screens.BLUE.identifier) {
        composable(
            Screens.BLUE.identifier,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.RED.identifier ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.RED.identifier ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screens.RED.identifier ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screens.RED.identifier ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { BlueScreen(navController) }
        composable(
            Screens.RED.identifier,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.BLUE.identifier ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    Screens.GREEN.identifier ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Screens.BLUE.identifier ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))
                    Screens.GREEN.identifier ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700))
                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    Screens.BLUE.identifier ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    Screens.GREEN.identifier ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700))
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Screens.BLUE.identifier ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))
                    Screens.GREEN.identifier ->
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700))
                    else -> null
                }
            }
        ) { RedScreen(navController) }
        navigation(
            startDestination = Screens.GREEN.identifier,
            route = "Inner",
            enterTransition = { expandIn(animationSpec = tween(700)) },
            exitTransition = { shrinkOut(animationSpec = tween(700)) }
        ) {
            composable(
                Screens.GREEN.identifier,
                enterTransition = {
                    when (initialState.destination.route) {
                        Screens.RED.identifier ->
                            slideIntoContainer(
                                AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                            )
                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        Screens.RED.identifier ->
                            slideOutOfContainer(
                                AnimatedContentScope.SlideDirection.Up, animationSpec = tween(700)
                            )
                        else -> null
                    }
                },
                popEnterTransition = {
                    when (initialState.destination.route) {
                        Screens.RED.identifier ->
                            slideIntoContainer(
                                AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                            )
                        else -> null
                    }
                },
                popExitTransition = {
                    when (targetState.destination.route) {
                        Screens.RED.identifier ->
                            slideOutOfContainer(
                                AnimatedContentScope.SlideDirection.Down, animationSpec = tween(700)
                            )
                        else -> null
                    }
                }
            ) { GreenScreen(navController) }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.BlueScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Spacer(Modifier.height(Dp(25f)))
        NavigateButton(
            "Navigate Horizontal",
            Modifier
                .wrapContentWidth()
                .then(Modifier.align(Alignment.CenterHorizontally))
        ) { navController.navigate(Screens.RED.identifier) }
        Spacer(Modifier.height(Dp(25f)))
        NavigateButton(
            "Navigate Expand",
            Modifier
                .wrapContentWidth()
                .then(Modifier.align(Alignment.CenterHorizontally))
        ) { navController.navigate("Inner") }
        Text(
            Screens.BLUE.label,
            modifier = Modifier.fillMaxWidth().weight(1f).animateEnterExit(
                enter = fadeIn(animationSpec = tween(250, delayMillis = 450)),
                exit = ExitTransition.None
            ),
            color = Color.White, fontSize = 80.sp, textAlign = TextAlign.Center
        )
        NavigateBackButton(navController)
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.RedScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Spacer(Modifier.height(Dp(25f)))
        NavigateButton(
            "Navigate Horizontal",
            Modifier
                .wrapContentWidth()
                .then(Modifier.align(Alignment.CenterHorizontally))
        ) { navController.navigate(Screens.BLUE.identifier) }
        Spacer(Modifier.height(Dp(25f)))
        NavigateButton(
            "Navigate Vertical",
            Modifier
                .wrapContentWidth()
                .then(Modifier.align(Alignment.CenterHorizontally))
        ) { navController.navigate(Screens.GREEN.identifier) }
        Text(
            Screens.RED.label,
            modifier = Modifier.fillMaxWidth().weight(1f).animateEnterExit(
                enter = fadeIn(animationSpec = tween(250, delayMillis = 450)),
                exit = ExitTransition.None
            ),
            color = Color.White, fontSize = 80.sp, textAlign = TextAlign.Center
        )
        NavigateBackButton(navController)
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibilityScope.GreenScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Spacer(Modifier.height(Dp(25f)))
        NavigateButton(
            "Navigate to ${Screens.RED.label}",
            Modifier
                .wrapContentWidth()
                .then(Modifier.align(Alignment.CenterHorizontally))
        ) { navController.navigate(Screens.RED.identifier) }
        Text(
            Screens.GREEN.label,
            modifier = Modifier.fillMaxWidth().weight(1f).animateEnterExit(
                enter = fadeIn(animationSpec = tween(250, delayMillis = 450)),
                exit = ExitTransition.None
            ),
            color = Color.White, fontSize = 80.sp, textAlign = TextAlign.Center
        )
        NavigateBackButton(navController)
    }
}

@Composable
fun NavigateButton(
    text: String,
    modifier: Modifier = Modifier,
    listener: () -> Unit = { }
) {
    Button(
        onClick = listener,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun NavigateBackButton(navController: NavController) {
    // Use LocalLifecycleOwner.current as a proxy for the NavBackStackEntry
    // associated with this Composable
    if (navController.currentBackStackEntry == LocalLifecycleOwner.current &&
        navController.previousBackStackEntry != null
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Go to Previous screen")
        }
    }
}