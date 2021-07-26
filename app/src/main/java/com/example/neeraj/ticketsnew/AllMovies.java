package com.example.neeraj.ticketsnew;

import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AllMovies extends Fragment {
    DatabaseReference ref;
    ArrayList<MovieHolder> movieList=new ArrayList<>();
    RecyclerView rview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_movies, container, false);

         rview = v.findViewById(R.id.recycler_view);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));

        ref = FirebaseDatabase.getInstance().getReference().child("movies");

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<MovieModel>().setQuery(ref,MovieModel.class).build();

        FirebaseRecyclerAdapter<MovieModel,MovieHolder> adapter=new FirebaseRecyclerAdapter<MovieModel, MovieHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MovieHolder holder, final int position, @NonNull final MovieModel model)
            {

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            holder.movie_name.setText(model.getMovie_name());
                            holder.desc.setText(model.getDescription());
                            Picasso.get().load(model.getUrl()).into(holder.movie_image);
                            holder.book.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v)
                                {
                                    SharedPreferences sp = getActivity().getSharedPreferences("name",0);
                                    SharedPreferences.Editor et = sp.edit();

                                    et.putString("movie_name",model.getMovie_name());
                                    et.commit();
                                    getFragmentManager().beginTransaction().replace(R.id.l1,new fragtwo()).commit();
                                }
                            });


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @NonNull
            @Override
            public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_container,parent,false);
                MovieHolder holder=new MovieHolder(view);
                return holder;
            }
        };
         rview.setAdapter(adapter);
         adapter.startListening();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder
    {
        TextView movie_name,desc;
        ImageView movie_image;
        Button book;

        public MovieHolder(View itemView) {
            super(itemView);

            movie_name=itemView.findViewById(R.id.name);
            movie_image=itemView.findViewById(R.id.movie_image);
            desc=itemView.findViewById(R.id.descrp);
            book=itemView.findViewById(R.id.book_btn);
        }
    }
}
