package com.example.securelayer.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelayer.R

@Composable
fun MedalFirstStep(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Primer Paso",
        subtitle = "Completaste tu\nprimera lección.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFBBDEFB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.first_step_ic),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalTropiezoValiente(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Tropiezo Valiente",
        subtitle = "Te equivocaste\npor primera vez.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFFFCDD2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.half_shield),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalRachaDeSiete(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Racha de 7",
        subtitle = "Usaste la app\n7 días seguidos.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFFFE0B2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.streak_seven_ic),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalBienvenido(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Bienvenido",
        subtitle = "Abriste la app\npor primera vez.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFC8E6C9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_ic),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalChispaDigital(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Chispa Digital",
        subtitle = "Alcanzaste\n250 XP.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFFFF9C4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sparkles_ic),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalGuardianNovato(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Guardián\nNovato",
        subtitle = "Alcanzaste\n500 XP.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFBBDEFB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.eye_check),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalMenteAlerta(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Mente Alerta",
        subtitle = "Alcanzaste\n1,000 XP.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFE1BEE7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.brain),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalCyberAgente(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Cyber\nAgente",
        subtitle = "Alcanzaste\n2,000 XP.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFC8E6C9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.spy),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalEscudoDeElite(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Escudo\nde Élite",
        subtitle = "Alcanzaste\n3,000 XP.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFFFE0B2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.crown),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

@Composable
fun MedalPlatino(modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = "Platino",
        subtitle = "Conseguiste\ntodas las medallas.",
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.trophy),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}

// Medalla que se desbloquea al alcanzar cierta XP
data class XpMedal(
    val requiredXp: Int,
    val title: String,
    val content: @Composable (Modifier) -> Unit
)

// Catálogo de medallas por XP (ordenadas de menor a mayor requisito)
val xpMedals = listOf(
    XpMedal(150, "Chispa Digital") { MedalChispaDigital(it) },
    XpMedal(500, "Guardián Novato") { MedalGuardianNovato(it) },
    XpMedal(1000, "Mente Alerta") { MedalMenteAlerta(it) },
    XpMedal(2000, "Cyber Agente") { MedalCyberAgente(it) },
    XpMedal(3000, "Escudo de Élite") { MedalEscudoDeElite(it) }
)

// Medallas ya desbloqueadas con cierta XP
fun unlockedMedals(totalXp: Int): List<XpMedal> =
    xpMedals.filter { it.requiredXp <= totalXp }

// Medallas que se desbloquean al pasar de oldXp a newXp (recién ganadas)
fun medalsUnlockedBetween(oldXp: Int, newXp: Int): List<XpMedal> =
    xpMedals.filter { it.requiredXp in (oldXp + 1)..newXp }

@Preview(showBackground = true)
@Composable
fun MedalPreview() {
    Row(modifier = Modifier.fillMaxWidth()) {
        MedalBienvenido(modifier = Modifier.weight(1f))
    }
}