package com.ilhomsoliev.paging.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.ilhomsoliev.paging.presentation.model.CharacterModel
import com.ilhomsoliev.paging.presentation.viewmodel.MainViewModel
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MainScreen(
    vm: MainViewModel
) {

    val charactersPagingState by vm.charactersPagingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background), content = {
            itemsIndexed(charactersPagingState.list, key = { _, item ->
                item.id
            }) { index, item ->
                LaunchedEffect(key1 = Unit, block = {
                    vm.onNewPosition(index)
                })
                Spacer(modifier = Modifier.height(4.dp))
                CharacterItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable {
                            // Navigate
                        }, characterModel = item

                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            item {
                if (charactersPagingState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        })
    }
}

@Composable
fun CharacterItem(
    modifier: Modifier,
    characterModel: CharacterModel
) {
    Row(modifier = modifier) {
        GlideImage(
            imageModel = characterModel.image,
            requestBuilder = Glide
                .with(LocalContext.current)
                .asBitmap()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .thumbnail(0.6f)
                .transition(withCrossFade()),
            modifier = Modifier.width(160.dp),
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colorScheme.surface, // TODO
                highlightColor = MaterialTheme.colorScheme.primary // TODO
            ),
            failure = {
                Text(text = "image request failed.")
            })
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = characterModel.name)
            // Status
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (characterModel.status == "Alive") Color.Green else if (characterModel.status == "Dead") Color.Red else Color.Gray)

                )
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = characterModel.status + " - " + characterModel.species
                )
            }
            // Location
            Column {
                Text(text = "Last known location:")
                Text(text = characterModel.location)
            }

            /*// First seen
            Text(text = "First seen in:")
            Text(text = if (characterModel.episode.isNotEmpty()) characterModel.episode[0] else "Unknown")*/

        }
    }
}