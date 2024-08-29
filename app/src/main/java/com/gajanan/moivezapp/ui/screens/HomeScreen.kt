package com.gajanan.moivezapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gajanan.moivezapp.models.PopularMovie
import com.gajanan.moivezapp.netwoek.Endpoints.IMAGE_BASE_URL
import com.gajanan.moivezapp.ui.viewModels.HomeViewModel

@Composable
fun HomeScreen( navigate : (Int) -> Unit ){
    val lifecycleObserver = LocalLifecycleOwner.current
    val viewModel = hiltViewModel<HomeViewModel>()
    var popularMovies by remember { mutableStateOf(emptyList<PopularMovie>()) }
    LaunchedEffect(viewModel) {
        viewModel.getAllPopularMovie.observe(lifecycleObserver) { result ->
            popularMovies = result.data?: emptyList()
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(popularMovies) { item ->
                PopularMovieItem(item = item) { id ->
                    navigate(id)
                }
            }
        }
    }
}
@Composable
fun PopularMovieItem(item : PopularMovie, onClick : (Int) -> Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable {
                onClick(item.id)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .width(100.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray),
            model = "$IMAGE_BASE_URL${item.poster_path}",
            contentDescription = "",
            clipToBounds = false,
            contentScale = ContentScale.FillBounds
            )
        Column ( modifier = Modifier.padding(start = 10.dp),){
            Text(
                text = item.title,
                color = Color.Black,
                fontWeight = FontWeight(600),
                fontSize = 16.sp
              )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Release Date: ${item.release_date}",
                fontSize = 12.sp
            )
            Text(
                text = "Average vote: ${item.vote_average}",
                fontSize = 12.sp
            )
        }
    }
}