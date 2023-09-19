package com.example.draggableitemspractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.draggableitemspractice.ui.theme.DraggableItemsPracticeTheme
import kotlin.random.Random

enum class DragAnchors(val fraction: Float) {
    Start(0f),
    Half(0.5f),
    End(1f)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DraggableItemsPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DraggableItemsApp()
                }
            }
        }
    }
}

@Composable
@Preview
fun DraggableItemsPreview() {
    DraggableItemsPracticeTheme {
        DraggableItemsApp()
    }
}

@Composable
fun DraggableItemsApp() {
    SnappingItemList(modifier = Modifier.fillMaxSize())
}

/**
 * A simple composable that shows a lazy column. Each item of the column takes up
 * the whole vertical height. Dragging upwards will snap back to the original item.
 * Dragging upwards enough (decided by threshold), will cause it to snap to next item
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnappingItemList(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight(),
        state = listState,
        flingBehavior = flingBehavior
    ) {
        items(3) {index ->
            val containerColor = Color(
                (Random.nextInt(256) + (index * 10)) % 256,
                (Random.nextInt(256) + (index * 10)) % 256,
                (Random.nextInt(256) + (index * 10)) % 256,
                255)
            val contentColor = containerColor.copy(
                red = containerColor.red * 0.5f,
                green = containerColor.green * 0.5f,
                blue = containerColor.blue * 0.5f)

            ItemCard(
                index = index,
                containerColor = containerColor,
                contentColor = contentColor,
                modifier = Modifier.fillParentMaxHeight()
            )

        }
    }

}

@Composable
fun ItemCard(containerColor: Color,
             contentColor: Color,
             index: Int,
             modifier: Modifier = Modifier) {
    Card (
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column (
            Modifier.fillMaxSize().padding(24.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically),
                text = "Item ${index + 1}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )
        }

    }
}
