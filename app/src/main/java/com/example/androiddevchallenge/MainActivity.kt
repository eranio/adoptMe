/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.data.PetsRepository
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val petsRepository = PetsRepository

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
//                HomeScreen(navController = rememberNavController())
            }
        }
    }
}

// Start building your app here!
@ExperimentalAnimationApi
@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, PetsRepository)
        }

        composable(
            "details/{pet}",
            arguments = listOf(navArgument("pet") {
           //     type = NavType.ParcelableType(Pet::class.java)
                type = NavType.StringType
            })
        ) { backStackEntry ->
            //val pet = backStackEntry.arguments?.getParcelable<Pet>("pet")
            val petName = backStackEntry.arguments?.getString("pet")
            PetDetailsScreen(petName)
        }
    }

//    Surface(color = MaterialTheme.colors.background) {
//        Text(text = "Ready... Set... GO!")
//    }
}

@ExperimentalAnimationApi
@Composable
fun HomeScreen(navController: NavController, petsRepository: PetsRepository) {
    MyTheme {
        PetsList(
            navController,
            //{ navController.navigate("details") },
            pets = petsRepository.pets
//            listOf(
//                Pet(0,"Luna", 2, R.drawable.dog1),
//                Pet(1,"Charlie", 6, R.drawable.dog2),
//                Pet(2,"Brandy", 3, R.drawable.dog3),
//                Pet(3,"Buddy", 4, R.drawable.dog4),
//                Pet(4,"Toffie", 7, R.drawable.dog5),
//                Pet(5,"Dixie", 2, R.drawable.dog6),
//                Pet(6,"Chase", 1, R.drawable.dog7),
//                Pet(7,"Laurie", 2, R.drawable.dog8),
//                Pet(8,"Billy", 3, R.drawable.dog9),
//                Pet(9,"Candy", 2, R.drawable.dog10),
//                Pet(10,"Bobbie", 5, R.drawable.dog11),
//                Pet(11,"Arrow", 2, R.drawable.dog12),
//                Pet(12,"Johnny", 4, R.drawable.dog13)
//            )
        )
    }
}

@Composable
fun PetDetailsScreen(pet: String?) {
    val name = "Luna"
  //  val petObj = navController.currentBackStackEntry?.arguments?.get("pet") as Pet

    MyTheme {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                //.height(100.dp)
                painter = painterResource(R.drawable.dog1),
                contentDescription = null
            )

            Box(modifier = Modifier.padding(12.dp)) {
                Spacer(modifier = Modifier.size(12.dp))
                Text("Name: ${name}")
            }
        }
    }
}

@Preview("Details", widthDp = 360, heightDp = 640)
@Composable
fun DetailsView() {
    MyTheme {
        PetDetailsScreen(null)
    }
}


@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        HomeScreen(navController = rememberNavController(), PetsRepository)
    }
}

@ExperimentalAnimationApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        HomeScreen(navController = rememberNavController(), PetsRepository)
    }
}

@Composable
fun PetRow(petName: String, petAge: Int, imageId: Int) {
    var showPopup by remember { mutableStateOf(false) }
    val onPopupDismissed = { showPopup = false }

    Surface(color = Color.LightGray) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.size(140.dp)
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(14.dp))
        ) {
            Column(modifier = Modifier.weight(4f)) {
                Text(text = "$petName")
                Spacer(Modifier.size(20.dp))
                Text(text = "Age: $petAge")
            }
            Image(
                modifier = Modifier
                    .weight(1f)
                    //.height(100.dp)
                    .clip(shape = RoundedCornerShape(14.dp)),
                painter = painterResource(imageId),
                contentDescription = null
            )

        }
    }

    if (showPopup) {
        AlertDialog(onDismissRequest = onPopupDismissed,
            text = {

                Text("Congrats! you just clicked a Composable")
            },
            confirmButton = {
                Button(onClick = onPopupDismissed) {
                    Text("OK")
                }
            })
    }
}

@ExperimentalAnimationApi
@Composable
fun PetsList(navController: NavController, pets: List<IPet>) {
    Box {
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            items(pets) { pet ->
                Card(
                    elevation = 4.dp,
                    modifier = Modifier.clickable {
//                        navController.currentBackStackEntry?.arguments?.putParcelable(
//                            "pet",
//                            pet as Parcelable
//                        )
                        navController.navigate("pet/${pet.getName()}")
//                        onClick()
                    }) {
//                        navController.navigate("details") }) {
                    PetRow(
                        petName = pet.getName(),
                        petAge = pet.getAge(),
                        imageId = pet.getImageId()
                    )
                }
            }
        }

        val showScrollToTopButton = listState.firstVisibleItemIndex > 0
        AnimatedVisibility(
            visible = showScrollToTopButton,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Button(onClick = {
                scope.launch {
                    listState.scrollToItem(0)
                }
            }) {
                Text("Top")
            }
        }

    }
}
