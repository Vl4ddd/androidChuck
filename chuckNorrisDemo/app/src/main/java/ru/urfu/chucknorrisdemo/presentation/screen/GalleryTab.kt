package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.urfu.chucknorrisdemo.domain.model.FactEntity

@Composable
fun GalleryTab() {
    val viewModel: GalleryViewModel = viewModel()
    val facts = viewModel.facts
    val currentPage = viewModel.currentPage

    if (facts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Нет данных для отображения")
        }
    } else {
        GalleryContent(
            facts = facts,
            currentPage = currentPage,
            onPageChanged = { page -> viewModel.currentPage = page }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GalleryContent(
    facts: List<FactEntity>,
    currentPage: Int,
    onPageChanged: (Int) -> Unit
) {
    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    LaunchedEffect(currentPage) {
        if (currentPage != pagerState.currentPage) {
            pagerState.animateScrollToPage(currentPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = facts.getOrNull(pagerState.currentPage)?.name ?: "",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            count = facts.size,
            state = pagerState,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) { page ->
            FactImageItem(fact = facts[page])
        }

        Spacer(modifier = Modifier.height(16.dp))

        DotsIndicator(
            totalDots = facts.size,
            selectedIndex = pagerState.currentPage
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = facts.getOrNull(pagerState.currentPage)?.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun FactImageItem(fact: FactEntity) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!fact.imageUrl.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = fact.imageUrl,
                    error = rememberAsyncImagePainter(
                        model = "https://via.placeholder.com/300x200/CCCCCC/666666?text=Нет+изображения"
                    )
                ),
                contentDescription = fact.name,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = "Нет изображения для: ${fact.name}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .padding(4.dp)
            ) {
                androidx.compose.material3.Surface(
                    color = if (index == selectedIndex)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    shape = androidx.compose.material3.MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxSize()
                ) {}
            }
        }
    }
}