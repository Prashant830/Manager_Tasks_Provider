package com.example.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

        List<Message> messages;

        public MessagesAdapter(final List<Message> messages) {
            this.messages = messages;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MessagesAdapter.ViewHolder holder, int position) {
            holder.messageContentTextView.setText(messages.get(position).messageContent);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView messageContentTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                messageContentTextView = itemView.findViewById(R.id.textView_message_content);
            }
        }

    }


