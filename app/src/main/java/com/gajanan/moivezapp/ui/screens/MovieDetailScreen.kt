package com.gajanan.moivezapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gajanan.moivezapp.netwoek.Endpoints.IMAGE_BASE_URL
import com.gajanan.moivezapp.ui.theme.Orange
import com.gajanan.moivezapp.ui.viewModels.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailScreen(movieId : String?=null){
    val viewModel = hiltViewModel<HomeViewModel>()
    val response = viewModel.movieState.value.movieDetailResponse
    var genres by remember { mutableStateOf(response?.genres) }
    LaunchedEffect(viewModel) {
        viewModel.getMovieDetails(movieId = movieId ?: return@LaunchedEffect)
    }
    LaunchedEffect(viewModel.movieState.value.movieDetailResponse) {
        genres = viewModel.movieState.value.movieDetailResponse?.genres
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(.3f)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray),
            model = "$IMAGE_BASE_URL${response?.backdrop_path}",
            contentDescription = "",
            clipToBounds = false,
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = response?.title ?: "No title",
            fontSize = 26.sp,
            color = Color.Black,
            fontWeight = FontWeight(700),
            modifier = Modifier.padding(10.dp)
            )

        FlowRow(
           modifier =  Modifier.fillMaxWidth()
        ) {
            genres?.forEach { gen ->
                Box(
                    Modifier
                        .wrapContentWidth()
                        .padding(5.dp)
                        .background(Orange)
                        .clip(RoundedCornerShape(3.dp))
                ) {
                    Text(
                       modifier =  Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        text = gen.name,
                        fontSize = 14.sp,
                        color = Color.White
                        )
                }
            }
        }
        Spacer(modifier = Modifier.padding(10.dp) )
        Text(
            text = "Overview: ",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontWeight = FontWeight(600)
            )
        Text(
            text = response?.overview ?: "N/A",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 10.dp),
            overflow = TextOverflow.Ellipsis,
            )
    }
}