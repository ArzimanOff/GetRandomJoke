package com.arziman_off.getrandomjoke;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.JokesViewHolder> {

    private ArrayList<JokeItemInfo> jokes = new ArrayList<>();

    public void setJokes(ArrayList<JokeItemInfo> jokes) {
        this.jokes = jokes;
    }

    @NonNull
    @Override
    public JokesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View jokeView = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.joke_item_view,
                        parent,
                        false
                );
        return new JokesViewHolder(jokeView);
    }

    @Override
    public void onBindViewHolder(JokesViewHolder viewHolder, int position) {
        JokeItemInfo jokeItem = jokes.get(position);

        viewHolder.tvJokeType.setText(jokeItem.getType());
        viewHolder.tvJokeSetup.setText(jokeItem.getSetup());
        viewHolder.tvJokePunchline.setText(jokeItem.getPunchline());
        viewHolder.tvJokeId.setText("id: " + jokeItem.getId().toString());
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }


    class JokesViewHolder extends RecyclerView.ViewHolder{
        private TextView tvJokeType;
        private TextView tvJokeSetup;
        private TextView tvJokePunchline;
        private TextView tvJokeId;
        public JokesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJokeType = itemView.findViewById(R.id.tvJokeTypeText);
            tvJokeSetup = itemView.findViewById(R.id.tvJokeSetup);
            tvJokePunchline = itemView.findViewById(R.id.tvJokePunchline);
            tvJokeId = itemView.findViewById(R.id.tvJokeIdText);
        }
    }
}
