package com.raineru.panatilihin.tempo

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raineru.panatilihin.R

private val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget justo euismod, porttitor nibh sit amet, ultricies orci. Vivamus maximus laoreet velit ac fringilla. Aliquam pretium tincidunt elit, vitae mattis urna gravida ut. Quisque sodales sed odio sed ultricies. Mauris gravida arcu in massa vulputate, id malesuada ligula ultricies. Ut quis ante vel dui efficitur dignissim ac ut ipsum. Sed vitae lectus ligula. Sed nec justo nec quam sollicitudin tempus. Nulla posuere sem nisl, eget mattis tellus convallis eu. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In id velit odio."

private val listSnacks = listOf(
    Snack("Cupcake", description, R.drawable.cupcake),
    Snack("Donut", description, R.drawable.donut),
    Snack("Eclair", description, R.drawable.eclair),
    Snack("Froyo", description, R.drawable.froyo),
    Snack("Gingerbread", description, R.drawable.gingerbread),
    Snack("Honeycomb", description, R.drawable.honeycomb),
)

// [START android_compose_shared_element_predictive_back]
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun SharedElement_PredictiveBack() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    navController,
                    this@SharedTransitionLayout,
                    this@composable
                )
            }
            composable(
                "details/{item}",
                arguments = listOf(navArgument("item") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("item")
                val snack = listSnacks[id!!]
                DetailsScreen(
                    navController,
                    id,
                    snack,
                    this@SharedTransitionLayout,
                    this@composable
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun DetailsScreen(
    navController: NavHostController,
    id: Int,
    snack: Snack,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Column(
            Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigateUp()
                }
        ) {
            Image(
                painterResource(id = snack.image),
                contentDescription = snack.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier.Companion
                    .sharedElement(
                        rememberSharedContentState(key = "image-$id"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            Text(
                snack.name, fontSize = 18.sp,
                modifier =
                Modifier.Companion
                    .sharedElement(
                        rememberSharedContentState(key = "text-$id"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .fillMaxWidth()
            )
            Text(
                snack.description, fontSize = 12.sp,
                modifier =
                Modifier.Companion
                    .sharedBounds(
                        rememberSharedContentState(key = "description-$id"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeScreen(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(listSnacks) { index, item ->
            Row(
                Modifier.clickable {
                    navController.navigate("details/$index")
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                with(sharedTransitionScope) {
                    Image(
                        painterResource(id = item.image),
                        contentDescription = item.description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.Companion
                            .sharedElement(
                                rememberSharedContentState(key = "image-$index"),
                                animatedVisibilityScope = animatedContentScope
                            )
                            .size(100.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            item.name, fontSize = 18.sp,
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(key = "text-$index"),
                                    animatedVisibilityScope = animatedContentScope,
                                )
                        )
                        Text(
                            item.description, fontSize = 12.sp,
                            modifier = Modifier
                                .sharedBounds(
                                    rememberSharedContentState(key = "description-$index"),
                                    animatedVisibilityScope = animatedContentScope,
                                )
                        )
                    }
                }
            }
        }
    }
}

data class Snack(
    val name: String,
    val description: String,
    @DrawableRes val image: Int
)
// [END android_compose_shared_element_predictive_back]