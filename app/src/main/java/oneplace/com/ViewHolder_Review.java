package oneplace.com;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder_Review extends RecyclerView.ViewHolder{
    TextView b_review_time;
    TextView b_review_user_name;
    TextView b_review_content;
    View view;



    public ViewHolder_Review(@NonNull View itemView) {
        super(itemView);

        b_review_time=itemView.findViewById(R.id.b_review_time);
        b_review_user_name=itemView.findViewById(R.id.b_review_user_name);
        b_review_content=itemView.findViewById(R.id.b_review_content);
        view = itemView;
    }
}
