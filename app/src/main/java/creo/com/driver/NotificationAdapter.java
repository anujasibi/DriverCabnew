package creo.com.driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context ctx;

    private int lastSelectedPosition = -1;

    private LayoutInflater inflater;

    //   private ArrayList<RecyclerPojo>recyclerPojos;

    private ArrayList<NotificationPojo>notificationPojos;

    // RecyclerView recyclerView;
    public NotificationAdapter(ArrayList<NotificationPojo> notificationPojos, Context context) {
        this.notificationPojos = notificationPojos;
        this.ctx = context;
    }



//     public RecyclerAdapter(Context ctx, ArrayList<RecyclerPojo> recyclerPojos){
//       this.ctx=ctx;
//
//     inflater = LayoutInflater.from(ctx);
//     this.recyclerPojos = recyclerPojos;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //  View view = inflater.inflate(R.layout.choose_address_row, parent, false);
        //  MyViewHolder holder = new MyViewHolder(view);

        // return holder;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.notification_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.name.setText(notificationPojos.get(position).getName());
        holder.img.setImageResource(notificationPojos.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return notificationPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;


        public MyViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.name);
            img =(ImageView) itemView.findViewById(R.id.imk);


        }
    }
}
