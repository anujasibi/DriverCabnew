package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.RatingBar;

import java.util.ArrayList;

public class Review extends AppCompatActivity {
    ArrayList<ReviewPojo> reviewPojoArrayList;
    private RecyclerView recyclerView;
    Context mContext=this;
    RatingBar mRatingBar;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        recyclerView = findViewById(R.id.recycler);
        final ArrayList<ReviewPojo> pojo = new ArrayList<>();
        ReviewPojo reviewPojo = new ReviewPojo("Anu SSS",R.drawable.person,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat");
        ReviewPojo reviewPojo1 = new ReviewPojo("Sara SSS",R.drawable.person2,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat");
        ReviewPojo reviewPojo2 = new ReviewPojo("Alan RR",R.drawable.person4,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat");
        ReviewPojo reviewPojo3 = new ReviewPojo("ANU SSS",R.drawable.person3,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat");
        ReviewPojo reviewPojo4 = new ReviewPojo("ANU SSS",R.drawable.person,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat");
        pojo.add(reviewPojo);
        pojo.add(reviewPojo1);
        pojo.add(reviewPojo2);
        pojo.add(reviewPojo3);
        pojo.add(reviewPojo4);
        ReviewAdapter reviewAdapter = new ReviewAdapter(mContext, pojo);

        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

}
