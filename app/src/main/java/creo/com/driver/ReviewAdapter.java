package creo.com.driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ReviewPojo> reviewPojos;

    public ReviewAdapter(Context ctx, ArrayList<ReviewPojo> reviewPojos){

        inflater = LayoutInflater.from(ctx);
        this.reviewPojos = reviewPojos;
    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.review_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.MyViewHolder holder, int position) {

      //  Picasso.get().load(reviewPojos.get(position).getImage()).into(holder.iv);
        holder.name.setText(reviewPojos.get(position).getName());
        holder.description.setText(reviewPojos.get(position).getDescription());
        holder.iv.setImageResource(reviewPojos.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return reviewPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView description, name;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.text);
            name = (TextView) itemView.findViewById(R.id.name);
            iv = (ImageView) itemView.findViewById(R.id.imj);
        }

    }
}