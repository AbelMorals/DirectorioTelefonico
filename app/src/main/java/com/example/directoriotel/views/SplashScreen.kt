package com.example.directorio.views

import androidx.annotation.RawRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.directoriotel.R
import com.example.onboardingapp.dataStore.OnboardingStore
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val title: String,
    val description: String,
    @RawRes val lottieFileRes: Int? = null,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingView(navController: NavController, onBoardingStore: OnboardingStore) {
    val scope = rememberCoroutineScope()

    val onboardingPages = listOf(
        OnboardingPageData(
            lottieFileRes = R.raw.page1,
            title = "Bienvenido a tu nuevo Directorio de Contactos",
            description = "Gestiona tus contactos de manera eficiente, todo en un solo lugar."
        ),
        OnboardingPageData(
            lottieFileRes = R.raw.page2,
            title = "Encuentra lo que Necesitas",
            description = "Todos tus contactos en un solo lugar, fáciles de encontrar."
        ),
        OnboardingPageData(
            lottieFileRes = R.raw.page3,
            title = "Actualizaciones Fáciles y Rápidas",
            description = "Modifica, añade o elimina información de tus contactos con solo unos pocos toques."
        ),
        OnboardingPageData(
            lottieFileRes = R.raw.page4,
            title = "Simple e Intuitivo",
            description = "Con una interfaz amigable y sencilla de navegar."
        ),
        OnboardingPageData(
            lottieFileRes = R.raw.page5,
            title = "Tus Contactos Clave",
            description = "Marca tus contactos más importantes como favoritos para acceder a ellos al instante."
        ),
        OnboardingPageData(
            lottieFileRes = R.raw.page6,
            title = "Elimina Contactos",
            description = "Elimina tus contactos con solo deslizar hacia la izquierda o derecha"
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
            OnboardingPageUi(pageData = onboardingPages[pageIndex],
                isVisible = pagerState.currentPage == pageIndex)
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
fun OnboardingPageUi(pageData: OnboardingPageData, isVisible: Boolean) {
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        contentVisible = isVisible
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 200)) +
                    slideInHorizontally(
                        animationSpec = tween(durationMillis = 500, delayMillis = 200),
                        initialOffsetX = { it / 2 }
                    ),
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (pageData.lottieFileRes != null) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(pageData.lottieFileRes))
                    val progress by animateLottieCompositionAsState(
                        composition,
                        iterations = LottieConstants.IterateForever
                    )
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        }

        // Título
        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 400))
        ) {
            Text(
                text = pageData.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp), // Padding dentro del contenido animado
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Descripción
        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 500))
        ) {
            Text(
                text = pageData.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp) // Padding dentro del contenido animado
            )
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) { iteration ->
            val isActive = currentPage == iteration

            val color by animateColorAsState(
                targetValue = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.2f
                ),
                animationSpec = tween(300),
                label = "Indicator Color Animation"
            )
            val size by animateDpAsState(
                targetValue = if (isActive) 12.dp else 8.dp,
                animationSpec = tween(300),
                label = "Indicator Size Animation"
            )

            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}