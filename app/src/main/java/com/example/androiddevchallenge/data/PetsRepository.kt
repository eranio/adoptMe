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
package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.IPet
import com.example.androiddevchallenge.Pet
import com.example.androiddevchallenge.R

object PetsRepository {
    var pets: List<IPet> = mutableListOf(
        Pet(0, "Luna", 2, R.drawable.dog1),
        Pet(1, "Charlie", 6, R.drawable.dog2),
        Pet(2, "Brandy", 3, R.drawable.dog3),
        Pet(3, "Buddy", 4, R.drawable.dog4),
        Pet(4, "Toffie", 7, R.drawable.dog5),
        Pet(5, "Dixie", 2, R.drawable.dog6),
        Pet(6, "Chase", 1, R.drawable.dog7),
        Pet(7, "Laurie", 2, R.drawable.dog8),
        Pet(8, "Billy", 3, R.drawable.dog9),
        Pet(9, "Candy", 2, R.drawable.dog10),
        Pet(10, "Bobbie", 5, R.drawable.dog11),
        Pet(11, "Arrow", 2, R.drawable.dog12),
        Pet(12, "Johnny", 4, R.drawable.dog13)
    )

    fun getPet(petId: Int): IPet? {
        return pets.find { pet -> pet.getId() == petId }
    }
}
