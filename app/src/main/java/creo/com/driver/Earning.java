package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Earning extends AppCompatActivity {

    LinearLayout linearChart;
    TextView withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        linearChart = (LinearLayout) findViewById(R.id.linearChart);
        withdraw=findViewById(R.id.wao);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Earning.this, PaymentList.class));
                Animatoo.animateZoom(Earning.this);
            }
        });
        int colerloop[] = { 1, 2, 1, 1,1,2,3 };
        int heightLoop[] = { 300, 100, 400, 200,300 ,300,500};
        for (int j = 0; j < colerloop.length; j++) {
            drawChart(1, colerloop[j], heightLoop[j]);
        }
    }

    public void drawChart(int count, int color, int height) {
        System.out.println(count + color + height);
        if (color == 3) {
            color =Color.RED;
        } else if (color == 1) {
            color =Color.GRAY;
        } else if (color == 2) {
            color = Color.GRAY;
        }

        for (int k = 1; k <= count; k++) {
            View view = new View(this);
            view.setBackgroundColor(color);
            view.setLayoutParams(new LinearLayout.LayoutParams(70, height));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
                    .getLayoutParams();
            params.setMargins(10, 5, 5, 0); // substitute parameters for left,
            // top, right, bottom
            view.setLayoutParams(params);
            linearChart.addView(view);
        }

    }
}
