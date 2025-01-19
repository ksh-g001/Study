package com.study.compose_ex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.compose_ex.ui.theme.Compose_exTheme


/*
1장 컴포즈 앱 첫 빌드
컴포즈의 등장 이유
    1. 다양해지는 디바이스 형태
    2. 다양해지는 기능 구현으로 인한 UI 선언(xml)이후 액티비티연결(binding를 비롯한 setContent류의 Content 관리)의 복잡성 증가 = 상용구 코드 양 급격히 증가
    3. 선언적 패러다임의 등장 <- 첫 대중화 React

컴포저블 함수
    - 컵포즈 앱의 핵심 구성 요소
    - 반환 타입 없음(반환 요소가 UI 제외하곤 없기 때문)
    - @Compose 어노테이션으로 선언
    - 상태(state)가 바뀌면 재구성이 발생하여 뷰를 재생성 -> remember로 값유지


Text = TextView
Button = Button
TextField = EditText
remember = remember API의 기본적인 함수로 Compose가 처음 생성되었을 때 특정 값을 메모리에 계속 유지하고 값이 변경됨을 감지하여 변경된 값을 반환해줌 (Rx?)
개인적 생각 : 아무래도 선언적 패러다임에 따른 방식이라 그런지 예전에 입문해 본 Flutter가 많이 떠오른다. 개발팀은 다르지만, 아무래도 같은 구글이 개발하기 때문에 더더욱 비슷한 듯하다. Jetpack Compose를 하는 사람들은 Flutter도 쉽게 할 듯하다. 프리뷰가 매번 빌딩을 해야 반영된다는 게 조금 거슬리긴 한데 xml의 프리뷰처럼 리빌딩없이 보여주는 방법은 없었을까?
*/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hello()
        }
    }
}

@Composable
fun Welcome() {
    Text(
        text = stringResource(id = R.string.welcome),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Hello(){
    val name = remember { mutableStateOf("") } //이거 rx 아녀???
    val nameEntered = remember { mutableStateOf(false) }//값이 바뀌면 반응
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        if(nameEntered.value) {
            Greeting(name = name.value)
        } else{
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Welcome()
                TextAndButton(name, nameEntered)
            }
        }
    }
}

@Composable
fun TextAndButton(name: MutableState<String>,
                  nameEntered: MutableState<Boolean>) {
    Row(modifier = Modifier.padding(top = 8.dp)){
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            placeholder = {
                Text(text = stringResource(id = R.string.app_name))
            },
            modifier = Modifier
                .alignByBaseline()
                .weight(1.0F),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions(onAny = {
                nameEntered.value = true
            })
        )
        Button(modifier = Modifier
            .alignByBaseline()
            .padding(8.dp),
            onClick = {
                nameEntered.value = true
            }){
            Text(text = stringResource(id=R.string.done))
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = stringResource(R.string.hello, name),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Compose_exTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun TextAndButtonPreview() {
    Compose_exTheme {
        val name = remember { mutableStateOf("") }
        val nameEntered = remember { mutableStateOf(false) }
        TextAndButton(name = name, nameEntered = nameEntered)
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    Compose_exTheme {
        Welcome()
    }
}