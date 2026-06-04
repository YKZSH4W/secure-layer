package com.example.securelayer.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelayer.R
import com.example.securelayer.data.model.Achievement

// Mapea el nombre del ícono guardado en la BD a un drawable de la app
fun mapMedalIcon(icon: String): Int = when (icon.lowercase().trim()) {
    "sparkles", "sparkles_ic" -> R.drawable.sparkles_ic
    "eye", "eye_check" -> R.drawable.eye_check
    "brain" -> R.drawable.brain
    "spy" -> R.drawable.spy
    "crown" -> R.drawable.crown
    "shield", "shield_medal" -> R.drawable.shield_route_ic
    "star", "star_ic" -> R.drawable.star_ic
    "trophy" -> R.drawable.trophy
    "first_step", "first_step_ic" -> R.drawable.first_step_ic
    "streak", "streak_seven_ic" -> R.drawable.streak_seven_ic
    else -> R.drawable.star_ic
}

// Color de fondo del ícono (la BD no guarda color: se asigna uno de una paleta)
private val medalColors = listOf(
    Color(0xFFFFF9C4), Color(0xFFBBDEFB), Color(0xFFE1BEE7),
    Color(0xFFC8E6C9), Color(0xFFFFE0B2), Color(0xFFFFCDD2)
)

fun medalColor(seed: Int): Color = medalColors[(seed % medalColors.size + medalColors.size) % medalColors.size]

// Tarjeta horizontal (ícono circular + nombre + descripción)
@Composable
fun AchievementCardHorizontal(achievement: Achievement, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(medalColor(achievement.id)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = mapMedalIcon(achievement.icon)),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(achievement.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(achievement.description, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

// Tarjeta vertical (para el row del perfil)
@Composable
fun AchievementCardVertical(achievement: Achievement, modifier: Modifier = Modifier) {
    MedalCard(
        modifier = modifier,
        title = achievement.name,
        subtitle = achievement.description,
        iconContent = {
            Box(
                modifier = Modifier.fillMaxSize().background(medalColor(achievement.id)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = mapMedalIcon(achievement.icon)),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    )
}
