package com.angad.mediadmin.screens

import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.angad.mediadmin.viewmodels.MyViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: MyViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val userState = viewModel.getAllUsers.collectAsState()

    LaunchedEffect(Unit){
        viewModel.getAllUsers()
    }

    when{
        userState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        userState.value.error != null -> {
            Toast.makeText(context, userState.value.error, Toast.LENGTH_SHORT).show()
        }

        userState.value.data != null -> {
            Toast.makeText(context, "Data fetch successfully", Toast.LENGTH_SHORT).show()

            val users = userState.value.data!!
            val totalUser = users.size
        //    Filtering the user
            val approvedUsers = users.count{it.isApproved == 1}
            val pendingUsers =  users.count{it.isApproved == 0}

            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(text = "Dashboard") })
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                   Card(
                       modifier = Modifier.fillMaxWidth()
                   ) {
                       Column {
                           Text(text = "Total Users: $totalUser")

                           Row {
                               Text(text = "Approved: $approvedUsers")
                               Text(text = "Pending: $pendingUsers")
                           }

                       //    For pie chart
                           PieChartView(approvedUsers, pendingUsers)
                       }
                   }
                }
            }
        }

    }

}

//    Composable function that make the pir chart of the approved and pending users
@Composable
fun PieChartView(approvedUsers: Int, pendingUsers: Int) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                isDrawHoleEnabled = true
                setUsePercentValues(true)
                setEntryLabelColor(Color.BLACK)
                setEntryLabelTextSize(12f)
                legend.isEnabled = true

                val entries = listOf(
                    PieEntry(approvedUsers.toFloat(), "Approved"),
                    PieEntry(pendingUsers.toFloat(), "Pending")
                )

                val dataSet = PieDataSet(entries, "User Status")
                dataSet.colors = listOf(ColorTemplate.COLORFUL_COLORS[0], ColorTemplate.COLORFUL_COLORS[1])
                dataSet.valueTextSize = 14f
                dataSet.valueTextColor = Color.WHITE

                data = PieData(dataSet)
                invalidate() // Refresh the chart
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}
