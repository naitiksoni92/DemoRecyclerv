package com.example.trainee6.demorecyclerv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Trainee6 on 2/1/2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{
    private List<Movie> moviesList;
    private Context context;
    private int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public Button btnDel;
        //LinearLayout container;
        public MyViewHolder(final View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.lbl_id);
            genre = (TextView) view.findViewById(R.id.lbl_hours);
            year = (TextView) view.findViewById(R.id.year);

            /*btnDel= (Button) view.findViewById(R.id.button2);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "Delete Button clicked!!"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }

    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
