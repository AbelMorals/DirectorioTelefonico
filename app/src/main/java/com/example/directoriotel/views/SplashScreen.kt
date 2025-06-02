package com.example.directorio.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.directoriotel.R
import com.example.onboardingapp.dataStore.OnboardingStore
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val imageRes: Int? = null,
    val title: String,
    val description: String,
    val iconForFeature: ImageVector? = null
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingView(navController: NavController, onBoardingStore: OnboardingStore) {
    val scope = rememberCoroutineScope()

    val onboardingPages = listOf(
        OnboardingPageData(
            imageRes = R.drawable.dictel,
            title = "Bienvenido al Directorio Telefonico",
            description = "Este directorio sirve para gestionar todos tus contactos de forma eficiente."
        ),
        OnboardingPageData(
            iconForFeature = Icons.Filled.Home,
            title = "Organización",
            description = "Todos tus contactos en un solo lugar, fáciles de encontrar."
        ),
        OnboardingPageData(
            iconForFeature = Icons.Filled.Person,
            title = "Actualizar",
            description = "Actualiza todos tus contactos de una manera facil y rapida."
        ),

        OnboardingPageData(
            iconForFeature = Icons.Filled.ThumbUp,
            title = "Facil de usar",
            description = "Con una interfaz amigable y sencilla de navegar."
        )
    )

    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            OnboardingPageUi(pageData = onboardingPages[pageIndex])
        }

        PageIndicator(
            pageCount = onboardingPages.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (pagerState.currentPage < onboardingPages.size - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                } else {
                    scope.launch {
                        onBoardingStore.saveBoarding(true)
                    }
                    navController.navigate("Home") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = if (pagerState.currentPage < onboardingPages.size - 1) "Siguiente" else "Comenzar Ahora",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun OnboardingPageUi(pageData: OnboardingPageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        pageData.imageRes?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = pageData.title,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit
            )
        }

        pageData.iconForFeature?.let {
            Icon(
                imageVector = it,
                contentDescription = "Icono de característica",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp).padding(bottom = 16.dp)
            )
        }

        Text(
            text = pageData.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = pageData.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .wrapContentSize(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .size(if (currentPage == iteration) 10.dp else 8.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}