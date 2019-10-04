package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import creo.com.driver.utils.SessionManager;

public class vehicledocument extends AppCompatActivity {

    CardView cardView,cardrc,cardpermit,cardinsurance,cardtourist,cardfit,cardnoc,cardpho;
    String make=null;
    String model=null;
    String year=null;
    String color=null;
    String plate=null;
    String rcbook=null;
    String permit=null;
    String vehicle_insurance=null;
    String tourist_permit=null;
    String vehicle_fitness=null;
    String noc=null;
    String pho=null;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicledocument);
        sessionManager = new SessionManager(this);

        make=sessionManager.getMake();
        model=sessionManager.getModel();
        year=sessionManager.getYear();
        color=sessionManager.getColor();
        plate=sessionManager.getPlate();
        pho=sessionManager.getPho();


        rcbook=sessionManager.getRC();
        permit=sessionManager.getPermit();
        vehicle_insurance=sessionManager.getInsurance();
        tourist_permit=sessionManager.getTourist();
        vehicle_fitness=sessionManager.getFitness();
        noc=sessionManager.getNOC();

        cardView=findViewById(R.id.vechile);
        cardrc=findViewById(R.id.rc);
        cardpermit=findViewById(R.id.permit);
        cardinsurance=findViewById(R.id.insurance);
        cardtourist=findViewById(R.id.tourist);
        cardfit=findViewById(R.id.fit);
        cardnoc=findViewById(R.id.noc);
        cardpho=findViewById(R.id.pho);




        if(make.equals("")||model.equals("")||year.equals("")||color.equals("")||plate.equals("")){
            cardView.setVisibility(View.VISIBLE);
        } if(!(make.equals("")||model.equals("")||year.equals("")||color.equals("")||plate.equals(""))){
            cardView.setVisibility(View.GONE);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,vehicledetails.class));
            }
        });

        if(rcbook.equals("")){
            cardrc.setVisibility(View.VISIBLE);
        }
        if(!(rcbook.equals(""))){
            cardrc.setVisibility(View.GONE);
        }

        cardrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,rcbook.class));
            }
        });

        if(permit.equals("")){
            cardpermit.setVisibility(View.VISIBLE);
        }
        if(!(permit.equals(""))){
            cardpermit.setVisibility(View.GONE);
        }

        cardpermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,taxipermit.class));
            }
        });

        if(vehicle_insurance.equals("")){
            cardinsurance.setVisibility(View.VISIBLE);
        }
        if(!(vehicle_insurance.equals(""))){
            cardinsurance.setVisibility(View.GONE);
        }

        cardinsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,vechicleinsurance.class));
            }
        });
        if(tourist_permit.equals("")){
            cardtourist.setVisibility(View.VISIBLE);
        }
        if(!(tourist_permit.equals(""))){
            cardtourist.setVisibility(View.GONE);
        }
        cardtourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,touristpermit.class));
            }
        });

        if(vehicle_fitness.equals("")){
            cardfit.setVisibility(View.VISIBLE);
        }
        if(!(vehicle_fitness.equals(""))){
            cardfit.setVisibility(View.GONE);
        }

        cardfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,vehiclefitness.class));
            }
        });

        if(noc.equals("")){
            cardnoc.setVisibility(View.VISIBLE);
        }
        if(!(noc.equals(""))){
            cardnoc.setVisibility(View.GONE);
        }

        cardnoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,Noc.class));
            }
        });
        if(pho.equals("")){
            cardpho.setVisibility(View.VISIBLE);
        }
        if(!(pho.equals(""))){
            cardpho.setVisibility(View.GONE);
        }

        cardpho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,vechiclephoto.class));
            }
        });
    }
}
