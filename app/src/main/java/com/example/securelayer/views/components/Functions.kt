package com.example.securelayer.views.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.securelayer.R
import com.example.securelayer.views.theme.SecureBlue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.Font
import com.example.securelayer.data.model.QuizQuestion
import com.example.securelayer.views.viewmodel.QuizViewModel

//Colores usados en varias interfaces
val SecureGreen = Color(0xFF2E7D32)

// Funcion para mostrar una tarjeta de consejo o recomendación de seguridad
@Composable
fun ConsejoCard(
    title: String,
    subtitle: String,
    titleColor: Color,
    iconContainerColor: Color,
    onClick: () -> Unit = {},
    iconContent: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(iconContainerColor),
                    contentAlignment = Alignment.Center
                ) {
                    iconContent()
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = titleColor)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = subtitle, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ver más", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF37474F))
                Icon(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "Ir",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// Funcion para mostrar una lección o curso con su estado de progreso
@Composable
fun LessonCard(icon: Int,
               title: String,
               desc: String,
               status: String,
               statusColor: Color,
               textColor: Color = Color.Black,
               onClick: () -> Unit
)
{
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(75.dp))

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Surface(color = statusColor, shape = RoundedCornerShape(16.dp)) {
                Text(status, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), fontSize = 11.sp, fontWeight = FontWeight.Bold,color = textColor)
            }
        }
    }
}

// Funcion para mostrar logros o medallas del usuario
@Composable
fun MedalCard(
    modifier: Modifier,
    title: String,
    subtitle: String,
    iconContent: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF7FAFD)),
                contentAlignment = Alignment.Center
            ) {
                iconContent()
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, fontSize = 11.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}



// Funcion para mostrar un campo de texto personalizado con estilo de la aplicación
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SecureBlue,
            unfocusedBorderColor = SecureBlue,
            focusedLabelColor = SecureBlue
        )
    )
}

//Función para mostrar la información de los consejos
@Composable
fun AdviceInfoCard(
    title: String,
    info: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(17.dp))

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SecureBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = info,
                fontSize = 18.sp,
                color = Color(0xFF424750)
            )
        }
    }
}
@Composable
fun BasicConceptCard(
    title: String,
    concept: String,
    advice: String,
    rule: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(17.dp))

            Text(title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = SecureBlue)

            Spacer(modifier = Modifier.height(10.dp))

            Text("Concepto: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)

            Text(concept,
                fontSize = 18.sp,
                color = Color(0xFF424750))

            Spacer(modifier = Modifier.height(8.dp))

            Text("El consejo: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)

            Text(advice,
                fontSize = 18.sp,
                color = Color(0xFF424750))

            Spacer(modifier = Modifier.height(8.dp))

            Text("Regla de Oro: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)

            Text(rule,
                fontSize = 18.sp,
                color = Color(0xFF424750))
        }
    }
}

@Composable
fun QuizActivityCard(
    index: Int,
    question: QuizQuestion,
    viewModel: QuizViewModel
) {
    val selected = viewModel.selectedAnswers[index]?: ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(12.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = question.question,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF181C1E)
            )

            Spacer(modifier = Modifier.height(10.dp))

            question.answers.forEach { answer ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectAnswer(index, answer) }
                ) {
                    RadioButton(
                        selected = selected == answer,
                        onClick = { viewModel.selectAnswer(index, answer) }
                    )

                    Text(
                        text = answer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF424750)
                    )
                }
            }
        }
    }
}

// Funcion para mostrar un item en la interfaz de ruta con icono y titulo
@Composable
fun LearningNode(
    iconId: Int,
    label: String,
    containerColor: Color,
    isLocked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCurrent: Boolean = false,
    iconSize: androidx.compose.ui.unit.Dp = 45.dp,
    labelColor: Color? = null
) {
    Column(
        modifier = modifier.clickable(enabled = !isLocked) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .border(
                    4.dp,
                    when {
                        isLocked -> Color(0xFFC3C6D1)
                        else -> Color.White
                    },
                    CircleShape
                )
                .clip(CircleShape)
                .background(if (isLocked) Color(0xFFE0E3E6) else containerColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = if (isLocked) Color(0xFF7E8186) else Color.White,
                modifier = Modifier.size(iconSize)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                color = labelColor ?: (if (isCurrent) SecureBlue else if (isLocked) Color.Transparent else Color(0xFF9AF6BE)),
                shape = RoundedCornerShape(16.dp),
                modifier = if (isLocked) Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)) else Modifier
            ) {
                Text(
                    text = label,
                    color = if (isLocked) Color.Gray else if (isCurrent) Color.White else Color(0xFF006D42),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            if (isLocked) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.lock_ic),
                    contentDescription = "Bloqueado",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Función para Mostrar un consejo del dia en diferentes apartados
@Composable
fun TipOfDayCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    iconContent: @Composable () -> Unit,
    containerColor: Color = Color(0xFFFFF9E6),
    contentColor: Color = Color(0xFF856404)
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconContent()

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
fun secureLayerLogo() {
    Box(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = R.drawable.waves), contentDescription = "Waves", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 60.dp)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shield),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(55.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "SecureLayer",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// Boton primario personalizado
@Composable
fun CustomPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = SecureBlue,
    icon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(55.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.invoke()

            if (icon != null) Spacer(modifier = Modifier.width(8.dp))

            Text(text, color = Color.White, fontSize = 16.sp)
        }
    }
}

// Boton secundario personalizado
@Composable
fun CustomOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(55.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, SecureBlue)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.invoke()
            if (icon != null) Spacer(modifier = Modifier.width(8.dp))
            Text(text, color = SecureBlue, fontSize = 16.sp)
        }
    }
}