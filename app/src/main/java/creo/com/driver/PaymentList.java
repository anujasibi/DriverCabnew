package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class PaymentList extends AppCompatActivity {
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);
          rl=findViewById(R.id.rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentList.this, card.class));
                Animatoo.animateCard(PaymentList.this);
            }
        });

    }
}
