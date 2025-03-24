package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val orderState = viewModel.getAllOrders.collectAsState()

//    For scroll
    val scrollState  = rememberScrollState()

    LaunchedEffect(Unit){
        viewModel.getAllUsers()
    }

    LaunchedEffect(Unit){
        viewModel.getAllOrders()
    }

    when{
        userState.value.isLoading || orderState.value.loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        userState.value.error != null || orderState.value.error != null -> {
            Toast.makeText(context, userState.value.error, Toast.LENGTH_SHORT).show()
        }

        userState.value.data != null || orderState.value.data != null -> {
            Toast.makeText(context, "Data fetch successfully", Toast.LENGTH_SHORT).show()

        //    Fetching the user details
            val users = userState.value.data!!
            val totalUser = users.size
            val approvedUsers = users.count{it.isApproved == 1}
            val pendingUsers =  users.count{it.isApproved == 0}

        //    Fetching the order details
            val orders = orderState.value.data!!
            val totalOrders = orders.size
            val approvedOrders = orders.count{it.isApproved == 1}
            val pendingOrders = orders.count{it.isApproved == 0}

        // Calculate total sales amount from only approved orders
            val totalSalesAmount = orders.filter { it.isApproved == 1 }.sumOf { it.total_price }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(
                            text = "Dashboard",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        ) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF66A9EC),
                            titleContentColor = Color.White
                        )
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color(0xFFE3F2FD))
                        .verticalScroll(scrollState)
                ) {
                //     User approved and pending pie chart card
                   Card(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(8.dp),
                       colors = CardDefaults.cardColors(
                           containerColor = Color(0xFFFFFFFF) // White Card Color
                       )
                   ) {
                       Column(
                           modifier = Modifier.padding(16.dp).fillMaxWidth(),
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Text(
                               text = "Total Users: $totalUser",
                               fontWeight = FontWeight.Bold,
                               fontSize = 20.sp
                           )

                           Row(
                               modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 10.dp)
                           ) {
                               Text(
                                   text = "Approved: $approvedUsers",
                                   fontWeight = FontWeight.SemiBold,
                                   fontSize = 16.sp,
                                   modifier = Modifier.weight(1f),
                                   textAlign = TextAlign.Start
                               )

                               Text(
                                   text = "Pending: $pendingUsers",
                                   fontWeight = FontWeight.SemiBold,
                                   fontSize = 16.sp,
                                   modifier = Modifier.weight(1f),
                                   textAlign = TextAlign.End
                               )
                           }

                       //    For pie chart of user status
                           PieChartView(approvedUsers, pendingUsers)
                       }
                   }

                //    Total sell amount card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFFFF) // White Card Color
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total Sells Amount:",
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "₹ $totalSalesAmount",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )
                        }
                    }

                //    Order status pie chart card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFFFF) // White Card Color
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Text(
                                text = "Total Orders: $totalOrders",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = "Approved: $approvedOrders",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = "Pending: $pendingOrders",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }

                            //    For pie chart of order status
                            OrderPieChart(approvedOrders, pendingOrders)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(100.dp))

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
                setEntryLabelColor(android.graphics.Color.BLACK)
                setEntryLabelTextSize(12f)
                legend.isEnabled = true

                val entries = listOf(
                    PieEntry(approvedUsers.toFloat(), "Approved"),
                    PieEntry(pendingUsers.toFloat(), "Pending")
                )

                val dataSet = PieDataSet(entries, "User Status")
                dataSet.colors = listOf(ColorTemplate.COLORFUL_COLORS[0], ColorTemplate.COLORFUL_COLORS[1])
                dataSet.valueTextSize = 14f
                dataSet.valueTextColor = android.graphics.Color.WHITE

                data = PieData(dataSet)
                invalidate() // Refresh the chart
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}


@Composable
fun OrderPieChart(approvedOrders: Int, pendingOrders: Int) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                isDrawHoleEnabled = true
                setUsePercentValues(true)
                setEntryLabelColor(android.graphics.Color.BLACK)
                setEntryLabelTextSize(12f)
                legend.isEnabled = true

                val entries = listOf(
                    PieEntry(approvedOrders.toFloat(), "Approved"),
                    PieEntry(pendingOrders.toFloat(), "Pending")
                )

                val dataSet = PieDataSet(entries, "User Status")
                dataSet.colors = listOf(ColorTemplate.COLORFUL_COLORS[0], ColorTemplate.COLORFUL_COLORS[1])
                dataSet.valueTextSize = 14f
                dataSet.valueTextColor = android.graphics.Color.WHITE

                data = PieData(dataSet)
                invalidate() // Refresh the chart
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}