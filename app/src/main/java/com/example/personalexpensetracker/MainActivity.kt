package com.example.personalexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.personalexpensetracker.ui.theme.PersonalExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalExpenseTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Ingredient(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
) {
    val backgroundColor = Color(0xFFF2E5D4)
    val borderColor = Color(0xFF866F4F)

    ConstraintLayout(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .border(BorderStroke(width = 1.dp, color = borderColor))
    ) {
        val horizontalGuideline50 = createGuidelineFromTop(0.5f)
        val imgIcon = createRef()

        Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.constrainAs(imgIcon) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(horizontalGuideline50)
            height = Dimension.fillToConstraints
        })


    }
}

@Preview(showBackground = true)
@Composable
fun IngredientPreview() {
    PersonalExpenseTrackerTheme {
        Ingredient(
            modifier = Modifier.size(100.dp),
            icon = R.mipmap.ic_launcher
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PersonalExpenseTrackerTheme {
        Greeting("Android")
    }
}