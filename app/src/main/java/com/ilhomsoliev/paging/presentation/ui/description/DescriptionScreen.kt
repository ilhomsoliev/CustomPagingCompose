package com.ilhomsoliev.paging.presentation.ui.description

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilhomsoliev.paging.presentation.viewmodel.DescriptionViewModel
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionScreen(
    vm: DescriptionViewModel,
    navController: NavController,
    characterId: Int,
) {

    val character by vm.character.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        Log.d("Hello", characterId.toString())
        vm.loadCharacter(characterId)
    })

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = character?.name ?: "", color = MaterialTheme.colorScheme.tertiary)
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                character?.let { characterModel ->
                    GlideImage(
                        imageModel = characterModel.image,
                        requestBuilder = Glide
                            .with(LocalContext.current)
                            .asBitmap()
                            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                            .autoClone()
                            .transition(BitmapTransitionOptions.withCrossFade()),

                        modifier = Modifier.fillMaxWidth(),
                        shimmerParams = ShimmerParams(
                            baseColor = MaterialTheme.colorScheme.surface,
                            highlightColor = MaterialTheme.colorScheme.primary
                        ),
                        failure = {
                            Text(text = "image request failed.")
                        })

                }
            }
        }
    }

}