package br.senai.sp.jandira.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat.FontCallback
import br.senai.sp.jandira.bmicalculator.calculate.calculate
import br.senai.sp.jandira.bmicalculator.calculate.getBmiClassification
import br.senai.sp.jandira.bmicalculator.ui.theme.BMICalculatorTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            BMICalculatorTheme {
                CalculatorScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorScreen() {

    var weightState by rememberSaveable {
        mutableStateOf("")
    }
    var heightState by rememberSaveable{
        mutableStateOf("")
    }
    var bmiState by rememberSaveable{
        mutableStateOf("0.0")
    }
    var bmiClassificationState by rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current.applicationContext
    val context2 = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            //Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bmi),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)

                )
                Text(
                    text = stringResource(id = R.string.title),
                    fontSize = 32.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            }
            //Form
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(text = stringResource(id = R.string.weight_label))
                OutlinedTextField(
                    value = weightState,
                    onValueChange = {
                        Log.i("ds2m", it)
                        weightState = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                )
                Text(
                    text = stringResource(id = R.string.height_label),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                OutlinedTextField(
                    value = heightState,
                    onValueChange = {
                        Log.i("ds2m", it)
                        heightState = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(

                    onClick = {
                        var w = weightState.toDouble()
                        var h = heightState.toDouble()
                        var bmi = calculate(weight = w, height =  h)
                              bmiState = String.format("%.1f", bmi)
                        bmiClassificationState = getBmiClassification(bmi, context)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.button_calculate),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            //Footer
            Column() {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(
                        topStart = 48.dp,
                        topEnd = 48.dp
                    ),
                    backgroundColor = Color(
                        79,
                        54,
                        232
                    ),
                    //border = BorderStroke(8.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.your_score),
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = bmiState,
                            color = Color.White,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = bmiClassificationState,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Row() {
                            Button(onClick = {
                                weightState = ""
                                heightState = ""
                                bmiClassificationState = ""
                                bmiState = ""
                            }) {
                                Text(text = stringResource(id = R.string.Reset))

                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Button(onClick = {
                                val openOther = Intent(context2, SignUpActivity::class.java)
                                context2.startActivity(openOther)
                            }) {
                                Text(text = stringResource(id = R.string.Share))
                            }

                        }
                    }
                }
            }
        }
    }
}
